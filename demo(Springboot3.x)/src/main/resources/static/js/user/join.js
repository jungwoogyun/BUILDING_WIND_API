        //----------------------------------------------------------------
        // 유효성 체크
        //----------------------------------------------------------------
         const isValid = ()=>{
             const form = document.joinform;

            if(form.username.value.trim()==""){
                alert("USERNAME을 입력하세요.");
                return;
            }
            if(form.password.value.trim()==""){
                alert("PASSWORD을 입력하세요.");
                return;
            }
             form.submit();
         }
        //----------------------------------------------------------------
        // EMAIL 발송
        //----------------------------------------------------------------
        const email_auth_btn_el = document.querySelector('.email_auth_btn');
        const username = document.querySelector('.username');
        email_auth_btn_el.addEventListener('click',function(){
            //alert('TEST');

            axios.get('/user/auth/email/'+username.value)
            .then(response=>{ console.log(response);   })
            .catch(error=>{});

        });
        //----------------------------------------------------------------
        // CODE 전송
        //----------------------------------------------------------------
        const email_auth_btn_2_el = document.querySelector('.email_auth_btn_2');
        const code = document.querySelector('.code');
        email_auth_btn_2_el.addEventListener('click',function(){
            //alert('TEST');

            const username_msg = document.querySelector('.username_msg');

            axios.get('/user/auth/confirm/'+code.value)
            .then(response=>{
                    console.log(response);
                    if(response.data === 'success'){
                        username_msg.innerHTML="이메일 인증 성공..!";
                        username_msg.style.color="green";
                    }else{
                        username_msg.innerHTML="이메일 인증 실패..!";
                        username_msg.style.color="red";
                    }
                    //모달창 종료
                    const modal_exit = document.querySelector('.modal_exit');
                    modal_exit.click();
            })
            .catch(error=>{});
        });