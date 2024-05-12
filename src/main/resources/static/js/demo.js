
$(document).ready(function(){

$(window).on('load', function(){
var particles = '<div class="particle-place-holder"><div class="particle"></div>'
for (let step = 0; step < 29; step++) {
  // Runs 5 times, with values of step 0 through 4.
  particles+='<div class="particle"></div>';
}
particles+="</div>"

$('.invi-page').each(function(index, item){
    if(item.className.includes("invi-page-gallary")) {
        return;
    }
    $(item).prepend(particles);
   });
});


  $(window).scroll(function() {
    doAnimations();
  });
  $(window).trigger('scroll');

//modal
    $(window).on('click', function(e) {
        if(e.target.id == 'myModal') {
            $('#myModal').removeClass("showModal");
        }
    });

  $('#intro-contact-button-trigger-modal').on("click", function(){
    $('#myModal').addClass("showModal");
  })

  $(".close").on("click", function() {
     $('#myModal').removeClass("showModal");
  })
  //modal

 //account copy
 $(".account-number").on("click", function() {
    var accountNumber = this.innerHTML
    // Copy the text inside the text field
   navigator.clipboard.writeText(accountNumber);

   // Alert the copied text
   alert("계좌번호 복사! " + accountNumber);
 });
 //account copy

 //kakao pay

 //kakao pay

  //calendar
month = document.getElementById("month");

function printMonth() {

    // 현재 날짜를 나타내는 Date 객체 생성
    var currentDate = new Date();

    // 현재 월을 가져옴
    var currentMonth = 8;
    var weddingDate = 3;

    //영어로 바꾸기
    var monthNames = [
    "일월의", "이월의", "삼월의", "사월의", "오월의", "유월의",
    "칠월의", "팔월의", "구월의", "시월의", "십일월의", "십이월의"
    ];

    var dayNames = [
        "하루날",
        "이틀날",
        "사흘날",
        "나흘날",
        "닷새날",
        "엿새날",
        "이레날",
        "여드레날",
        "아흐레날",
        "열흘날",
        "열하루날",
        "열이틀날",
        "열사흘날",
        "열나흘날",
        "열닷새날",
        "열엿새날",
        "열이레날",
        "열여드레날",
        "열아흐레날",
        "스무날",
        "스무하루날",
        "스무이틀날",
        "스무사흘날",
        "스무나흘날",
        "스무닷새날",
        "스무엿새날",
        "스무이에날",
        "스물 여드리네날",
        "스물 아흐레날",
        "거른 날",
        "그믐날"
    ]
    var currentMonthName = monthNames[currentMonth];
    var weddingDayName = dayNames[weddingDate];

    month.innerHTML = "<h2 class='eb-garamond-font title-font'>" + currentMonthName + " " + weddingDayName + "...</h2>";
}


//이번 달 달력 그리기
calendar_box = document.getElementById("calendar");

function printCalendar(y, m) {

    //① 현재 날짜와 현재 달에 1일의 날짜 객체를 생성합니다.
    var date = new Date('2021-09-04'); //날짜 객체 생성
    var nowY = date.getFullYear(); //현재 연도
    var nowM = date.getMonth(); //현재 월
    var nowD = date.getDate(); //현재 일

    y = (y != undefined) ? y : nowY;
    m = (m != undefined) ? m-1 : nowM;

    /* 현재 월의 1일에 요일을 구합니다.
     그럼 그달 달력에 첫 번째 줄 빈칸의 개수를 구할 수 있습니다.*/
    var theDate = new Date(y, m, 1);
    var theDay = theDate.getDay();

    //② 현재 월에 마지막 일을 구해야 합니다.

    //1월부터 12월까지 마지막 일을 배열로 저장함.
    var last = [31,28,31,30,31,30,31,31,30,31,30,31];
    /*현재 연도가 윤년(4년 주기이고 100년 주기는 제외합니다.
    또는 400년 주기)일경우 2월에 마지막 날짜는 29가 되어야 합니다.*/
    if (y % 4 == 0 && y % 100 !=0 || y % 400 == 0) lastDate=last[1]=29;

    var lastDate = last[m]; //현재 월에 마지막이 몇일인지 구합니다.

    //③ 현재 월의 달력에 필요한 행의 개수를 구합니다.
    var row = Math.ceil((theDay+lastDate)/7); //필요한 행수

    //④ 달력 년도/월 표기 및  달력 테이블 생성
    calendar_box.innerHTML = "<h2>" + y + "." + (m+1) + "</h2>";

    //문자결합 연산자를 사용해 요일이 나오는 행을 생성
    var calendar = "<table border='1'>";
    calendar += "<tr>";
    calendar += "<th>월</th>";
    calendar += "<th>화</th>";
    calendar += "<th>수</th>";
    calendar += "<th>목</th>";
    calendar += "<th>금</th>";
    calendar += "<th>토</th>";
    calendar += "<th>일</th>";
    calendar += "</tr>";

    var dNum = 1;
    //이중 for문을 이용해 달력 테이블을 생성
    for (var i = 1; i <= row; i++) {//행 생성 (tr 태그 생성)
        calendar += "<tr>";

        for (var k = 1; k <= 7; k++) {//열 생성 (td 태그 생성)
            /*행이 첫 줄이고 현재 월의 1일의 요일 이전은 모두 빈열로
            표기하고 날짜가 마지막 일보다 크면 빈열로 표기됩니다.*/
            if (i == 1 && k < theDay || dNum > lastDate) {
                calendar += "<td> &nbsp; </td>";
            }
            else {
                // 오늘 날짜에 대한 스타일 적용
                if (dNum === nowD) {
                    calendar += "<td id='today'>" + dNum + "</td>";
                } else {
                    calendar += "<td>" + dNum + "</td>";
                }
                dNum++;
            }
        }
    calendar += "<tr>";
    }

    //⑤ 문자로 결합된 달력 테이블을 문서에 출력
    calendar_box.innerHTML = calendar;
}

//함수 실행
printMonth();
printCalendar();
  //calendar


//countdown
const clock = document.getElementById('count-down');
// 카운트다운 타이머 구현
const Timer = (date) => {
    const targetDate = new Date(date).getTime(); // 목표 시간 설정
    const nowDate = new Date().getTime(); // 현재 시간 설정
    const countDate = targetDate - nowDate; // 남은 시간을 밀리초로 반환
    const days = Math.floor((countDate / (1000 * 60 * 60 * 24))); // 일
    clock.innerText = `${days}일 남았습니다.`
}

setInterval(Timer, 1000, '2021-09-04'); // 2022년 6월 31일을 Timer의 date 인자로 넘김

//countdown

//map
var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
var options = { //지도를 생성할 때 필요한 기본 옵션
	center: new kakao.maps.LatLng(37.510995784666186, 127.0977152693481), //지도의 중심좌표.
	level: 4 //지도의 레벨(확대, 축소 정도)
};

var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
// 마커가 표시될 위치입니다
var markerPosition  = new kakao.maps.LatLng(37.510995784666186, 127.0977152693481);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);
//map


//방명록
$(".write-message-button").on('click', function(e) {
    $('#recipient-name').val('');
    $('#message-text').val('');
});

$('#post-message-button').on('click', function(e) {
    var today = new Date();
    var year = today.getFullYear();
    var month = ('0' + (today.getMonth() + 1)).slice(-2);
    var day = ('0' + today.getDate()).slice(-2);
    var hours = ('0' + today.getHours()).slice(-2);
    var minutes = ('0' + today.getMinutes()).slice(-2);
    var recipientName = $('#recipient-name').val();
    var messageText = $('#message-text').val();

    $('.guest-message-card').last().after(
            '<div class="guest-message-card">'
                + '<div class="guest-message-audit">'
                    + '<p class="guest-message-created-at eb-garamond-font">'
                        + year + '.' + month  + '.' + day + ' ' + hours + ':' + minutes + '</p>'
                    + '<p class="guest-message-created-by eb-garamond-font">' + recipientName + '</p>'
                + '</div>'
                + '<p class="eb-garamond-font guest-message-content">' + messageText  + '</p>'
            + '</div>'
      );
});
//방명록

//rsvp 신랑신부 구분
$('.side').on('click', function() {
    $('.side').each(function() {
        $(this).removeClass('side-groom').removeClass('side-bride');
    })
    if($(this).text() == '신랑') {
        $(this).addClass('side-groom');
        $('')
    } else if($(this).text() == '신부') {
        $(this).addClass('side-bride');
    }
});
//rsvp 신랑신부 구분





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

