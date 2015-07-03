package lyc.com.dragstylecodestandard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by lyc on 2015/7/2.
 */
public class DragableLisvtView extends ListView {
    private static final int DRAG_IMG_SHOW = 1;
    private static final int DRAG_IMG_NOT_SHOW = 0;
    private double AMP_FACTOR = 1.2;

    public DragableLisvtView(Context context) {
        super(context);
        init();
    }

    public DragableLisvtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragableLisvtView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        setOnItemLongClickListener(longClick);
        dragIv = new ImageView(getContext());
        dragIv.setTag(DRAG_IMG_NOT_SHOW);
        lps = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private int prePosition;
    private WindowManager windowManager;
    private WindowManager.LayoutParams lps;
    private ImageView dragIv;
    private int downRawX;
    private int downRawY;
    private boolean isViewOnDrag = false;
    private View currentItem;
    private OnItemLongClickListener longClick = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            prePosition = i;
            currentItem=view;

            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(true);

            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

            lps.gravity = Gravity.TOP | Gravity.LEFT;

            lps.width = (int) (bitmap.getWidth() * AMP_FACTOR);
            lps.height = (int) (bitmap.getHeight() * AMP_FACTOR);
            lps.x = downRawX - lps.width / 2;
            lps.y = downRawY - lps.height / 2;
            lps.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            lps.format = PixelFormat.TRANSLUCENT;
            lps.windowAnimations = 0;

            if ((int) dragIv.getTag() == DRAG_IMG_SHOW) {
                windowManager.removeView(dragIv);
                dragIv.setTag(DRAG_IMG_NOT_SHOW);
            }
            dragIv.setImageBitmap(bitmap);
            isViewOnDrag = true;

            windowManager.addView(dragIv, lps);
            dragIv.setLayoutParams(lps);
            Log.e("lyc", "iv width height" + dragIv.getLayoutParams().width + "  " + dragIv.getLayoutParams().height);
            dragIv.setTag(DRAG_IMG_SHOW);
            view.setVisibility(INVISIBLE);
//            ((GridViewAdapter)getAdapter()).h


            return true;
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downRawX = (int) ev.getRawX();
            downRawY = (int) ev.getRawY();
        } else if ((ev.getAction() == MotionEvent.ACTION_MOVE) && isViewOnDrag) {
            Log.e("lyc", "event" + ev.getRawX() + " " + ev.getRawY());
            lps.x = (int) (ev.getRawX() - dragIv.getWidth() / 2);
            lps.y = (int) (ev.getRawY() - dragIv.getHeight() / 2);

            windowManager.updateViewLayout(dragIv, lps);
            Log.e("lyc", "move iv" + String.valueOf(dragIv.getX()));


        } else if ((ev.getAction() == MotionEvent.ACTION_UP) && isViewOnDrag) {
            currentItem.setVisibility(VISIBLE);
            if ((int)(dragIv.getTag()) == DRAG_IMG_SHOW) {
                windowManager.removeView(dragIv);
                dragIv.setTag(DRAG_IMG_NOT_SHOW);
            }
            int currDragPosition = pointToPosition((int) ev.getX(), (int) ev.getY());

            if ((currDragPosition != AdapterView.INVALID_POSITION) && (currDragPosition != prePosition)) {
                ((CustomAdapter)getAdapter()).swapItem(prePosition, currDragPosition);
            }
            isViewOnDrag = false;
        }
        return super.onTouchEvent(ev);
    }
}
