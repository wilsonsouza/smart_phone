package br.com.tk.mcs.components;

/**
 * Created by wilsonsouza on 10/02/17.
 */

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * README: Must be called on UI-Thread (Main thread), so post to a handler if needed
 * <p>
 * NOTES:
 * - To enable easier debugging, tag your nodes with android:tag="Name-of-my-node"
 * - Avoid using wrap_content on ScrollView!
 * - If you have center or right/bottom gravity, you should re-layout all nodes, not only the wrap_content: just call the method with the boolean set to true
 */
public class update_content_impl
{
	public static final String TAG = update_content_impl.class.getName ( );
	
	/**
	 * Does what a proper requestLayout() should do about layout_width or layout_height = "wrap_content"
	 * <p>
	 * Warning: if the subTreeRoot itself has a "wrap_content" layout param, the size will be computed without boundaries maximum size.
	 * If you do have limits, consider either passing the parent, or calling the method with the size parameters (View.MeasureSpec)
	 *
	 * @param subTreeRoot root of the sub tree you want to recompute
	 */
	public static final void invalidate ( ViewGroup subTreeRoot )
	{
		invalidate ( subTreeRoot,
		             false,
		             MeasureSpec.UNSPECIFIED,
		             MeasureSpec.UNSPECIFIED
		           );
	}
	
	/**
	 * Same but allows re-layout of all views, not only those with "wrap_content". Necessary for "center", "right", "bottom",...
	 */
	public static final void invalidate ( ViewGroup subTreeRoot,
	                                      boolean relayoutAllNodes
	                                    )
	{
		invalidate ( subTreeRoot,
		             relayoutAllNodes,
		             MeasureSpec.UNSPECIFIED,
		             MeasureSpec.UNSPECIFIED
		           );
	}
	
	/**
	 * Same as previous, but with given size in case subTreeRoot itself has layout_width or layout_height = "wrap_content"
	 */
	public static void invalidate ( ViewGroup subTreeRoot,
	                                boolean relayoutAllNodes,
	                                int subTreeRootWidthMeasureSpec,
	                                int subTreeRootHeightMeasureSpec
	                              )
	{
		assert ( "Main".equals ( Thread.currentThread ( )
		                               .getName ( ) ) );
		
		if ( subTreeRoot == null )
		{
			return;
		}
		LayoutParams layoutParams = subTreeRoot.getLayoutParams ( );
		
		// --- First, we force measure on the subTree
		int widthMeasureSpec = subTreeRootWidthMeasureSpec;
		// When LayoutParams.MATCH_PARENT and Width > 0, we apply measured width to avoid getting dimensions too big
		if ( layoutParams.width != LayoutParams.WRAP_CONTENT && subTreeRoot.getWidth ( ) > 0 )
		{
			widthMeasureSpec = MeasureSpec.makeMeasureSpec ( subTreeRoot.getWidth ( ),
			                                                 MeasureSpec.EXACTLY
			                                               );
		}
		int heightMeasureSpec = subTreeRootHeightMeasureSpec;
		// When LayoutParams.MATCH_PARENT and Height > 0, we apply measured height to avoid getting dimensions too big
		if ( layoutParams.height != LayoutParams.WRAP_CONTENT && subTreeRoot.getHeight ( ) > 0 )
		{
			heightMeasureSpec = MeasureSpec.makeMeasureSpec ( subTreeRoot.getHeight ( ),
			                                                  MeasureSpec.EXACTLY
			                                                );
		}
		// This measure recursively the whole sub-tree
		subTreeRoot.measure ( widthMeasureSpec,
		                      heightMeasureSpec
		                    );
		
		// --- Then recurse on all children to correct the sizes
		recurse_wrap_content ( subTreeRoot,
		                       relayoutAllNodes
		                     );
		
		// --- RequestLayout to finish properly
		subTreeRoot.requestLayout ( );
		return;
	}
	
	/**
	 * Internal method to recurse on view tree. Tag you View nodes in XML layouts to read the logs more easily
	 */
	private static void recurse_wrap_content ( View nodeView,
	                                           boolean relayoutAllNodes
	                                         )
	{
		// Does not recurse when visibility GONE
		if ( nodeView.getVisibility ( ) == View.GONE )
		{
			// nodeView.layout( nodeView.getLeft(), nodeView.getTop(), 0, 0 );		// No need
			return;
		}
		
		LayoutParams layoutParams = nodeView.getLayoutParams ( );
		boolean      isWrapWidth  = ( layoutParams.width == LayoutParams.WRAP_CONTENT ) || relayoutAllNodes;
		boolean      isWrapHeight = ( layoutParams.height == LayoutParams.WRAP_CONTENT ) || relayoutAllNodes;
		
		if ( isWrapWidth || isWrapHeight )
		{
			
			boolean changed = false;
			int     right   = nodeView.getRight ( );
			int     bottom  = nodeView.getBottom ( );
			
			if ( isWrapWidth && nodeView.getMeasuredWidth ( ) > 0 )
			{
				right = nodeView.getLeft ( ) + nodeView.getMeasuredWidth ( );
				changed = true;
			}
			if ( isWrapHeight && nodeView.getMeasuredHeight ( ) > 0 )
			{
				bottom = nodeView.getTop ( ) + nodeView.getMeasuredHeight ( );
				changed = true;
			}
			
			if ( changed )
			{
				nodeView.layout ( nodeView.getLeft ( ),
				                  nodeView.getTop ( ),
				                  right,
				                  bottom
				                );
			}
		}
		
		// --- Recurse
		if ( nodeView instanceof ViewGroup )
		{
			ViewGroup nodeGroup = ( ViewGroup ) nodeView;
			for ( int i = 0; i < nodeGroup.getChildCount ( ); i++ )
			{
				recurse_wrap_content ( nodeGroup.getChildAt ( i ),
				                       relayoutAllNodes
				                     );
			}
		}
		return;
	}
	// End of class
}