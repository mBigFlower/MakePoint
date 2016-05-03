package com.flowerfat.path;

import android.animation.ValueAnimator;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2016/4/30.
 */
public class PathAnim {

    private static final int DEFAULT_ANIM_FRAME = 300;

    private List<PathPoint> mAnimPoints = new ArrayList<>();

    public PathAnim() {

    }

    private void addLine(PathLine pathLine, int frame) {
        if (frame == 0) {
            // TODO 抛出异常
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
        for (int i = 1; i < frame; i++) {
            // 点位变化
            pointX = pointX + frameXLength;
            pointY = pointY + frameYLength;
            // 加入后续的点
            mAnimPoints.add(new PathPoint(pointX, pointY, false));
        }
    }

    private int mFrame;
    private int tempFrame;

    private void anim(final Path path, int animDuration, int startDelay) {
        if (mAnimPoints.size() == 0) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mAnimPoints.size() - 1);
        valueAnimator.setDuration(animDuration);
        valueAnimator.setStartDelay(startDelay);
        path.moveTo(mAnimPoints.get(0).x, mAnimPoints.get(0).y);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tempFrame = (int) animation.getAnimatedValue();
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

        /**
         * 动画开始执行
         */
        public void onStart() {
            PathAnim pathAnim = new PathAnim();
            for (PathLine pathLine : params.pathLines) {
                pathAnim.addLine(pathLine, DEFAULT_ANIM_FRAME);
            }
            if (params.animDuration == 0) {
                params.animDuration = 1000;
            }
            if (params.animPath == null) {
                throw new NullPointerException("You should bindPath for this builder first");
            }
            pathAnim.anim(params.animPath, params.animDuration, params.animStartDelay);
        }

        public static class Params {
            Path animPath;
            int animDuration;
            int animStartDelay;
            List<PathLine> pathLines = new ArrayList<>();
        }
    }
}
