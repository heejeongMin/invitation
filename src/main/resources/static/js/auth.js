

$(document).ready(function(){
    document.cookie.split(";").forEach((element) => {
        if(element.trim().startsWith("token") && element.split("token=")[1]) {
            $("#auth").text("로그아웃");
        } else {
            $("#auth").text("로그인");
        }
    })

    $(".login").on('click', function() {
        login();
        console.log("test");
    });

    function login() {
        const client_id = 'client_id=dffccdd7bda4104d3214311ae335fd78';
        const redirect_uri = 'redirect_uri=http://localhost:8080/login';
        const response_type = 'response_type=code';

        const url = 'https://kauth.kakao.com/oauth/authorize?'
            + client_id + '&' + redirect_uri + '&' + response_type;

//         window.open(url);
        location.href = url;
     }


})