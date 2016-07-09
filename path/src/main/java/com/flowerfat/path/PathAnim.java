package com.flowerfat.path;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2016/4/30.
 * <p/>
 * 这里有个坑，坑，坑！每条线分成多少个点是有讲究的（把门姑且称之为帧）。
 * 1s的属性动画，它的属性值会更新62次，计算方式：t*60+2次
 * (为啥是60？我试出来的。。。这个应该和手机硬件啥的有关，我姑且用它)
 * 所以，为了每次更新属性值的时候，每个值都走一遍，我们要计算这个frame（帧）
 * 公式是这样：(lineNumber是添加的直线的条数，t是动画的时间)
 * frame * lineNumber
 * ----------------------- < 1
 * 60 * t
 * 求解frame即可 直接用：60*t/lineNumber/1000 ('/1000'的目的是s和ms的转换)
 */
public class PathAnim {

    // 记录所有要做动画的点
    private List<PathPoint> mAnimPoints = new ArrayList<>();
    // 属性动画的进度
    private int mFrame;
    private int tempFrame;

    public PathAnim() {

    }

    private void addLine(PathLine pathLine, int frame) {
        if (frame == 0) {
            throw new NullPointerException("the frame must be not 0");
        }
        // 计算线的横向长度
        float lineX = pathLine.endX - pathLine.beginX;
        // 计算线的纵向长度
        float lineY = pathLine.endY - pathLine.beginY;
        // 计算每一帧代表的长度（X）
        float frameXLength = lineX / frame;
        // 计算每一帧代表的长度（Y）
        float frameYLength = lineY / frame;
        // 加入第一个点
        float pointX = pathLine.beginX;
        float pointY = pathLine.beginY;
        mAnimPoints.add(new PathPoint(pathLine.beginX, pathLine.beginY, true)); // TODO 这里的true有必要吗？
        for (int i = 1; i < frame - 1; i++) {
            // 点位变化
            pointX = pointX + frameXLength;
            pointY = pointY + frameYLength;
            // 加入后续的点
            mAnimPoints.add(new PathPoint(pointX, pointY, false));
        }
        mAnimPoints.add(new PathPoint(pathLine.endX, pathLine.endY, false));
    }

    private void anim(final Path path, int animDuration, int startDelay, int repeatCount) {
        if (mAnimPoints.size() == 0) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mAnimPoints.size() - 1);
        valueAnimator.setDuration(animDuration)
                .setStartDelay(startDelay);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(repeatCount);
        path.moveTo(mAnimPoints.get(0).x, mAnimPoints.get(0).y);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tempFrame = (int) animation.getAnimatedValue();
//                Log.w("pathAnim", tempFrame + " " + mAnimPoints.size());
                if (mFrame != tempFrame) {
                    mFrame = tempFrame;
                    if (mAnimPoints.get(mFrame).isDown) {
                        path.moveTo(mAnimPoints.get(mFrame).x, mAnimPoints.get(mFrame).y);
                    } else {
                        path.lineTo(mAnimPoints.get(mFrame).x, mAnimPoints.get(mFrame).y);
                    }
                }

            }
        });
        valueAnimator.start();
    }

    public static class Builder {
        Params params;

        public Builder() {
            params = new Params();
        }

        public Builder setAnimDuration(int animDuration) {
            params.animDuration = animDuration;
            return this;
        }

        public Builder setStartDelay(int animStartDelay) {
            params.animStartDelay = animStartDelay;
            return this;
        }

        ////////////////////////////////////////////////////////
        // 位移相关开始
        ////////////////////////////////////////////////////////
        public Builder addLine(PathLine pathLine) {
            params.pathLines.add(pathLine);
            return this;
        }

        public Builder addLine(float beginX, float beginY, float endX, float endY) {
            params.pathLines.add(new PathLine(beginX, beginY, endX, endY));
            return this;
        }

        public Builder addLinePoint(float endX, float endY) {
            int size = params.pathLines.size();
            if (size == 0)
                params.pathLines.add(new PathLine(0, 0, endX, endY));
            else {
                params.pathLines.add(new PathLine(params.pathLines.get(size - 1).endX, params.pathLines.get(size - 1).endY,
                        endX, endY));
            }
            return this;
        }

        public Builder toRelWhere(float relX, float relY) {
            int size = params.pathLines.size();
            if (size == 0)
                params.pathLines.add(new PathLine(0, 0, relX, relY));
            else {
                params.pathLines.add(new PathLine(params.pathLines.get(size - 1).endX, params.pathLines.get(size - 1).endY,
                        params.pathLines.get(size - 1).endX + relX, params.pathLines.get(size - 1).endY + relY));
            }
            return this;
        }

        public Builder toRelX(float toRelX) {
            return toRelWhere(toRelX, 0);
        }

        public Builder toRelY(float toRelY) {
            return toRelWhere(0, toRelY);
        }
        ////////////////////////////////////////////////////////
        // 位移相关结束
        ////////////////////////////////////////////////////////

        /**
         * 绑定Path
         *
         * @param animPath
         * @return
         */
        public Builder bindPath(Path animPath) {
            params.animPath = animPath;
            return this;
        }

        public Builder setRepeat(int repeatCount) {
            params.repeatCount = repeatCount ;
            return this;
        }

        /**
         * 动画开始执行
         */
        public void onStart() {
            PathAnim pathAnim = new PathAnim();
            if (params.animPath == null) {
                throw new NullPointerException("You should bindPath for this builder first");
            }
            if (params.animDuration == 0) {
                params.animDuration = 1000;
            }
            // 计算每条线被分成多少段（帧）
            int frame = 60 * params.animDuration / params.pathLines.size() / 1000;
            // 把所有的线都转化为Path需要的点
            for (PathLine pathLine : params.pathLines) {
                pathAnim.addLine(pathLine, frame - 1);
            }
            pathAnim.anim(params.animPath, params.animDuration, params.animStartDelay
                    , params.repeatCount);
        }

        public static class Params {
            Path animPath;
            int animDuration;
            int animStartDelay;
            int repeatCount;
            List<PathLine> pathLines = new ArrayList<>();
        }
    }
}
