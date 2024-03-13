

$(document).ready(function(){
  $('.date-picker').dateAndTime();

  $('#address').on("click", function() {
     new daum.Postcode({
        oncomplete: function( data ) {
            $('#address').val(data.roadAddress);
        }
    }).open();
  });

  $(document).on('change','#intro-img' , function(){
        $('#frame').attr("src", URL.createObjectURL(event.target.files[0]));
   });

 $('#bride-firstname-en').on("change", function() {
    nameConfigSet();
 });
 $('#groom-firstname-en').on("change", function() {
     nameConfigSet();
  });

  $('.date-picker').on("change", function() {
    (event.target.type == 'date')
        ? $("#date-config").text(event.target.value)
        : $("#time-config").text(event.target.value)
  });

  $('#loc-name').on("change", function() {
    $('#loc-config').text(event.target.value);
  });

  $('.effect-config').on("click", function() {
    var effect = $(event.target).text();
    if(effect == '효과 없음') {
        $('.invi-wrapper-config > .particle').hide();
    } else if (effect == '효과 1') {
        $('.invi-wrapper-config > .particle').show();
    }
  });

  $(window).scroll(function() {
    console.log("test");
    doAnimations();
  });
//  $(window).trigger('scroll');

  function doAnimations() {
      // Calc current offset and get all animatables
      var offset = $(window).scrollTop() + $(window).height(),
          $animatables = $('.animatable');

      // Unbind scroll handler if we have no animatables
      if ($animatables.size() == 0) {
        $(window).off('scroll', doAnimations);
      }

      // Check all animatables and animate them if necessary
      $animatables.each(function(i) {
         var $animatable = $(this);
        if (($animatable.offset().top + $animatable.height() - 20) < offset) {
          $animatable.removeClass('animatable').addClass('animated');
        }
      });
    };


 function nameConfigSet() {
        var bride_lastname_kr = $('#bride-lastname-kr').val();
         var bride_firstname_kr = $('#bride-firstname-kr').val();
         var bride_lastname_en = $('#bride-lastname-en').val();
         var bride_firstname_en = $('#bride-firstname-en').val();

         var groom_lastname_kr = $('#groom-lastname-kr').val();
         var groom_firstname_kr = $('#groom-firstname-kr').val();
         var groom_lastname_en = $('#groom-lastname-en').val();
         var groom_firstname_en = $('#groom-firstname-en').val();

         $('#names-config').text(bride_firstname_en + "&" + groom_firstname_en)
 }

});
