
const localLogout = ()=>{
                    const provider = document.querySelector('.myProvider').innerText;

                    if(provider=='' || provider=='kakao'){
                        const form = document.logoutForm;
                        form.submit();
                    }
                    else if(provider=='google' ||provider=='naver' ){


                            //--------------------------------
                            //팝업창 크기 지정
                            //--------------------------------
                            var popupWidth = 450;
                            var popupHeight = 450;
                            var popupX = (window.screen.width / 2) - (popupWidth / 2);
                            var popupY= (window.screen.height / 2) - (popupHeight / 2);

                            var newWindow = window.open('/logoutPage', '_blank', 'status=no, height=' + popupHeight  + ', width=' + popupWidth  + ', left='+ popupX + ', top='+ popupY);

                               setTimeout(function() {
                                 // 1.5초후 창닫기
                                  newWindow.close();

                                 // 특정 지점으로 리다이렉션
                                 window.location.href = '/login';
                               }, 1500);
                    }
  };