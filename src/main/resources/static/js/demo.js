
$(document).ready(function(){
  $(window).scroll(function() {
    doAnimations();
  });
  $(window).trigger('scroll');

  function doAnimations() {
      // Calc current offset and get all animatables
      var offset = $(window).scrollTop() + $(window).height();
      var animatables = $('.animatable');

      // Unbind scroll handler if we have no animatables
      if (animatables.size == 0) {
        $(window).off('scroll', doAnimations);
      }

      // Check all animatables and animate them if necessary
      animatables.each(function(i) {
         var $animatable = $(this);
        if (($animatable.offset().top + $animatable.height() - 600) < offset) {
          $animatable.removeClass('animatable').addClass('animated');
        }
      });
    };
});
