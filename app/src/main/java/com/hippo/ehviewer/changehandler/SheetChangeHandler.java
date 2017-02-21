/*
 * Copyright 2017 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer.changehandler;

/*
 * Created by Hippo on 2/19/2017.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.hippo.ehviewer.R;
import com.hippo.ehviewer.changehandler.base.RecolorStatusBarTransitionChangeHandler;
import com.hippo.ehviewer.transition.CrossFade;
import com.hippo.ehviewer.widget.HeaderLayout;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionSet;

/**
 * ChangeHandler between two SheetController.
 */
public class SheetChangeHandler extends RecolorStatusBarTransitionChangeHandler {

  @Nullable
  @Override
  protected Transition getTransition3(@NonNull ViewGroup container, @Nullable View from,
      @Nullable View to, boolean isPush) {
    if (from == null || to == null) {
      return null;
    }

    if (from instanceof HeaderLayout) {
      ((HeaderLayout) from).getShadow().setVisibility(View.INVISIBLE);
    }

    return new TransitionSet()
        .setOrdering(TransitionSet.ORDERING_TOGETHER)
        .addTransition(new Recolor().addTarget(R.id.header))
        .addTransition(new CrossFade().addTarget(R.id.icon).addTarget(R.id.title))
        .addTransition(new ChangeBounds().addTarget(R.id.title))
        // Out fade whole the from view, in fade only scroll view of the to view
        // to make it look like scroll view cross fade.
        // TODO not work for pop
        .addTransition(new Fade(Fade.OUT)
            .addTarget(from.findViewById(R.id.scroll_view))
            .addTarget(from.findViewById(R.id.positive))
            .addTarget(from.findViewById(R.id.negative)))
        .addTransition(new Fade(Fade.IN)
            .addTarget(to.findViewById(R.id.scroll_view))
            .addTarget(to.findViewById(R.id.positive))
            .addTarget(to.findViewById(R.id.negative)));
  }

  @Override
  protected void resetFromView(@NonNull View from) {
    super.resetFromView(from);

    if (from instanceof HeaderLayout) {
      ((HeaderLayout) from).getShadow().setVisibility(View.VISIBLE);
    }
  }
}