package com.jiadu.easyrunner.hencode.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.*;
import com.socks.library.KLog;

public class TouchView extends View {
    public TouchView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.e(event.getAction());

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            performClick();

        }
        return true;
    }


public boolean onTouchEvent(MotionEvent event) {
    // 获取x、y的信息
    final float x = event.getX();
    final float y = event.getY();
    final int viewFlags = mViewFlags;
    // 没有用getActionMasked() 说明View并没有处理多点触摸；
    final int action = event.getAction();

    // 判断clickable
    final boolean clickable = ((viewFlags & CLICKABLE) == CLICKABLE
            || (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE)
            || (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE;

    // 判断是否不可用状态；DISABLED
    if ((viewFlags & ENABLED_MASK) == DISABLED) {
        if (action == MotionEvent.ACTION_UP && (mPrivateFlags & PFLAG_PRESSED) != 0) {
            setPressed(false);
        }
        mPrivateFlags3 &= ~PFLAG3_FINGER_DOWN;
        // A disabled view that is clickable still consumes the touch
        // events, it just doesn't respond to them.

        // 返回clickable，代表消费了这个事件；
        return clickable;
    }

    // mTouchDelegate 一般用来增大点击范围 [40:00]
    if (mTouchDelegate != null) {
        if (mTouchDelegate.onTouchEvent(event)) {
            return true;
        }
    }

    // TOOLTIP 在Api26中加入 [43:00]
    // android:tooltipText="长按会显示text内容，目的是说明按钮内容"
    if (clickable || (viewFlags & TOOLTIP) == TOOLTIP) {
        // 一个事件序列从DOWN-MOVE-UP、CANCEL,所以从DOWN事件看起；
        switch (action) {
            case MotionEvent.ACTION_UP:
                // 置为手未触摸屏幕
                mPrivateFlags3 &= ~PFLAG3_FINGER_DOWN;
                // handleTooltipUp 设置 TOOLTIP消失
                if ((viewFlags & TOOLTIP) == TOOLTIP) {
                    handleTooltipUp();
                }
                // 不可点击处理
                if (!clickable) {
                    removeTapCallback();
                    removeLongPressCallback();
                    mInContextButtonPress = false;
                    mHasPerformedLongPress = false;
                    mIgnoreNextUpEvent = false;
                    break;
                }
                boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
                if ((mPrivateFlags & PFLAG_PRESSED) != 0 || prepressed) {
                    // take focus if we don't have it already and we should in
                    // touch mode.
                    boolean focusTaken = false;
                    // isFocusable() 可以获取焦点，!isFocused() 未获取焦点
                    if (isFocusable() && isFocusableInTouchMode() && !isFocused()) {
                        focusTaken = requestFocus();
                    }

                    if (prepressed) {
                        // The button is being released before we actually
                        // showed it as pressed.  Make it show the pressed
                        // state now (before scheduling the click) to ensure
                        // the user sees it.
                        setPressed(true, x, y);
                    }

                    if (!mHasPerformedLongPress && !mIgnoreNextUpEvent) {
                        // This is a tap, so remove the longpress check
                        removeLongPressCallback();

                        // Only perform take click actions if we were in the pressed state
                        if (!focusTaken) {
                            // Use a Runnable and post this rather than calling
                            // performClick directly. This lets other visual state
                            // of the view update before click actions start.
                            if (mPerformClick == null) {
                                mPerformClick = new PerformClick();
                            }
                            if (!post(mPerformClick)) {
                                performClickInternal();
                            }
                        }
                    }

                    if (mUnsetPressedState == null) {
                        mUnsetPressedState = new UnsetPressedState();
                    }

                    if (prepressed) {
                        postDelayed(mUnsetPressedState,
                                ViewConfiguration.getPressedStateDuration());
                    } else if (!post(mUnsetPressedState)) {
                        // If the post failed, unpress right now
                        mUnsetPressedState.run();
                    }

                    removeTapCallback();
                }
                mIgnoreNextUpEvent = false;
                break;

            case MotionEvent.ACTION_DOWN:
                // 判断是不是摸到屏幕了，有其他触摸，比如实体按键
                if (event.getSource() == InputDevice.SOURCE_TOUCHSCREEN) {
                    mPrivateFlags3 |= PFLAG3_FINGER_DOWN;
                }
                mHasPerformedLongPress = false;

                // 如果是不可点击的，检查LongClick，为TOOLTIP提示处理
                if (!clickable) {
                    checkForLongClick(0, x, y);
                    break;
                }

                // performButtonActionOnTouchDown 检测鼠标右键点击，
                // 如果是，显示上下文菜单 ContextMenu
                if (performButtonActionOnTouchDown(event)) {
                    break;
                }

                // 是否在滑动容器中
                // Walk up the hierarchy to determine if we're inside a scrolling container.
                boolean isInScrollingContainer = isInScrollingContainer();

                // For views inside a scrolling container, delay the pressed feedback for
                // a short period in case this is a scroll.
                if (isInScrollingContainer) {
                    // 并不知道用户是想要滑动还是点击
                    // flags置为PFLAG_PREPRESSED，预按下状态；为之后的滑动做处理 [56:30]
                    mPrivateFlags |= PFLAG_PREPRESSED;
                    if (mPendingCheckForTap == null) {
                        // CheckForTap点击的等待器，是一个Runnable
                        mPendingCheckForTap = new CheckForTap();
                    }
                    mPendingCheckForTap.x = event.getX();
                    mPendingCheckForTap.y = event.getY();
                    // 延迟100ms处理
                    postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                } else {
                    // 不在滑动控件中，
                    // 点击的目的有两种，一是点击，二是长按，因此先变为按下状态；
                    // Not inside a scrolling container, so show the feedback right away
                    setPressed(true, x, y); // 1. 设置为按下状态；
                    checkForLongClick(0, x, y); // 2. 设置一个长按的等待器
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                if (clickable) {
                    setPressed(false);
                }
                removeTapCallback();
                removeLongPressCallback();
                mInContextButtonPress = false;
                mHasPerformedLongPress = false;
                mIgnoreNextUpEvent = false;
                mPrivateFlags3 &= ~PFLAG3_FINGER_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                if (clickable) {
                    // android 5.0 之后，有波纹效果，这里就是对应的处理
                    drawableHotspotChanged(x, y);
                }

                // Be lenient about moving outside of buttons
                // 如果手移出了View的范围后松手，则判定为点击失败 [1:06:20]，移除所有设置
                if (!pointInView(x, y, mTouchSlop)) {
                    // mTouchSlop [1:07:19]
                    // Slop 可以理解为溢出，比如手一动了多少距离算是厉害了当前View
                    // Outside button
                    // Remove any future long press/tap checks
                    removeTapCallback();
                    removeLongPressCallback();
                    if ((mPrivateFlags & PFLAG_PRESSED) != 0) {
                        setPressed(false);
                    }
                    mPrivateFlags3 &= ~PFLAG3_FINGER_DOWN;
                }
                break;
            default:
                break;
        }

        return true;
    }

    return false;
}

public boolean isInScrollingContainer() {
    // 递归，不定的查看父View的shouldDelayChildPressedState是否返回了true
    // shouldDelayChildPressedState 是否延迟子View的按下状态；
    ViewParent p = getParent();
    while (p != null && p instanceof ViewGroup) {
        if (((ViewGroup) p).shouldDelayChildPressedState()) {
            return true;
        }
        p = p.getParent();
    }
    return false;
}

private final class CheckForTap implements Runnable {
    public float x;
    public float y;

    @Override
    public void run() {
        // 置空预按下状态，置为不是预按下
        mPrivateFlags &= ~PFLAG_PREPRESSED;
        // 设置按下状态
        setPressed(true, x, y);
        // 设置长按的等待器
        checkForLongClick(ViewConfiguration.getTapTimeout(), x, y);
    }
}

private void checkForLongClick(int delayOffset, float x, float y) {
    // delayOffset
    // 不管滑动还是非滑动，500ms的等待时长是固定的，如果是滑动的，则需要减去 预 按下状态所花费的时间
    if ((mViewFlags & LONG_CLICKABLE) == LONG_CLICKABLE || (mViewFlags & TOOLTIP) == TOOLTIP) {
        mHasPerformedLongPress = false;

        if (mPendingCheckForLongPress == null) {
            mPendingCheckForLongPress = new CheckForLongPress();
        }
        mPendingCheckForLongPress.setAnchor(x, y);
        mPendingCheckForLongPress.rememberWindowAttachCount();
        mPendingCheckForLongPress.rememberPressedState();
        postDelayed(mPendingCheckForLongPress,
                ViewConfiguration.getLongPressTimeout() - delayOffset);
    }
}
}
