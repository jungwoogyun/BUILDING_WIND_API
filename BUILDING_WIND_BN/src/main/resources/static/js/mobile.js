  // 리사이즈 이벤트 핸들러 함수
  function handleResize() {
    var windowWidth = window.innerWidth;

    const modalEls = document.querySelectorAll('.modal-dialog');
    if (windowWidth <= 500) {
        modalEls.forEach((modal)=>{
            modal.classList.remove('modal-xl');
            modal.classList.remove('modal-fullsize');
            
            modal.classList.add('modal-fullscreen-sm-down');
            
        })

      
    } else {
      // 브라우저 크기가 600px 이상이면 메시지를 숨김
      modalEls.forEach((modal)=>{
        modal.classList.add('modal-xl');
        modal.classList.add('modal-fullsize');

        modal.classList.remove('modal-fullscreen-sm-down');
        })
    
    }
  }

  // 리사이즈 이벤트에 핸들러 함수 연결
  window.addEventListener('resize', handleResize);

  // 초기에 한 번 실행하여 현재 브라우저 크기에 대한 상태를 확인
  handleResize();