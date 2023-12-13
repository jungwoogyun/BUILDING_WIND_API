        //---------------------------------------
        //유효성 체크
        //---------------------------------------
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

    //-----------------------
    //로그인 form input 스타일링
    //-----------------------
    document.addEventListener('click',function(e){
        //console.log(e.target);
        console.log(e.target.className);
        if(e.target.className.includes('username') || e.target.className.includes('password'))
        {
            const siblingNode = e.target.nextSibling.nextSibling;
            siblingNode.style.top = '-12px';
            siblingNode.style.left = '10px';
            siblingNode.style.color = 'green';
            siblingNode.style.fontSize = '0.8rem';
            siblingNode.style.fontWeight = '500';
            siblingNode.style.backgroundColor='white';

            const parentNode = e.target.parentNode;
            parentNode.style.border='3px solid green';
            e.target.focus();
        }
        else if(e.target.className.includes('placeholder')){
            e.target.style.top = '-12px';
            e.target.style.left = '10px';
            e.target.style.color = 'green';
            e.target.style.fontSize = '0.8rem';
            e.target.style.fontWeight = '500';
            e.target.style.backgroundColor='white';
            const parentNode = e.target.parentNode;
            parentNode.style.border='3px solid green';
            const previousSiblingNode = e.target.previousSibling.previousSibling;
            previousSiblingNode.focus();
        }
        else if(e.target.tagName=='INPUT'){
                ;
        }
        else{

                const insert_el_group_els =  document.querySelectorAll('.insert-el-group');
                insert_el_group_els.forEach(el=>{
                console.log(el);
                const children = el.children;
                children[0].value='';
                children[0].blur();
                el.style.border ='1px solid lightgray';
                children[1].style.top = '10px';
                children[1].style.left = '20px';
                children[1].style.color = '#585858';
                children[1].style.fontSize = '0.9rem';
                children[1].style.fontWeight = '500';
                });

        }
    });
    //-----------------------
    //탭키눌렀을때(Focusing) 스타일
    //-----------------------
    const inputEls =  document.querySelectorAll('input');
    inputEls.forEach(inputEl=>{
        inputEl.addEventListener('keydown',function(event){
            if (event.key === "Tab") {
                   //console.log(event.target);
                   if(event.target.className.includes('username')){
                        const pwEl = document.querySelector('.password');
                        const siblingNode = pwEl.nextSibling.nextSibling;
                        siblingNode.style.top = '-12px';
                        siblingNode.style.left = '10px';
                        siblingNode.style.color = 'green';
                        siblingNode.style.fontSize = '0.8rem';
                        siblingNode.style.fontWeight = '500';
                        siblingNode.style.backgroundColor='white';

                        const parentNode = pwEl.parentNode;
                        parentNode.style.border='3px solid green';

                   }
                   console.log(event.target.tagName);
            }


        })
    });