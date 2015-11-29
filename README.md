# slidingMenu_viewPager_scrollView

slidingMenu_viewPager_scrollView is an Open Source Android app demo that shows how to customize slidingMenu and combine with 
viewPager and scrollView.

XML Usage
-----------------

If you decide to use SlidingMenu as a view, you can define it in your xml layouts like this:
```xml
<com.example.slidingmenu_viewpager_scrollview.components.SlidingMenu 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/sliding_menu"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
	<include layout="@layout/view_menu"/>
  <include layout="@layout/view_slide"/>
</com.example.slidingmenu_viewpager_scrollview.components.SlidingMenu>

```

NOTE:
- @layout/view_menu is your own menu layout
- @layout/view_slide is your own content layout

Developed By
-----------------
Eugene

License
-----------------
Copyright 2015  Eugene

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
