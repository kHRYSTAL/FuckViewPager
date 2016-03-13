##FuckViewPager


###Edit Reason
项目有个需求，需求需要上方的Tab选中居中
使用的库是[SmartTabLayout](https://github.com/ogaclejapan/SmartTabLayout)
同时要求下方的`ViewPager`左右能看到边界,滑动有缩放效果，最开始我是通过`clipChildren="false"`实现了未选中的页面可见效果，同时用`setPageTransformer(boolean b,ViewPager.Transformer t)`实现的滑动动画。

就在这个时候，操蛋的事情发生了 当使用了`setPageTransformer()`， 如果`ViewPager`内部是`Fragment`
`Fragment`内部是`Scrollable`控件(我的需求是纵向的`WebView`),当页面发生形变后，页面内容不能纵向滑动了

找了很多的解决办法 效果都不理想

最后自己封装一个包裹`ViewPager`的容器自己实现`PageTransformer`

###功能
 * 支持clipChildren
 * 支持滑动效果(待完善或添加自定义动画)
 * 支持只使用容器，自己添加ViewPager(待完善)
 * 添加滑动效果后，内容支持滚动

###使用方法


```java
  <me.khrystal.fuckviewpager.FuckViewPager
        android:id="@+id/viewpager"
        app:offscreenPageLimit="3"
        app:pagerMargin="10"
        app:paddingLeft="5"
        app:paddingRight="5"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
  </me.khrystal.fuckviewpager.FuckViewPager>
```

```java
    FuckViewPager pager = (FuckViewPager)findViewById(R.id.viewpager);
    pager.getViewPager().setAdapter(adapter);
```
