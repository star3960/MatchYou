document.addEventListener('DOMContentLoaded', () => {
    const imageUpload = document.getElementById('imageUpload');
    const fileUploadArea = document.querySelector('.file-upload-area');
    const fileSelectButton = document.querySelector('.file-select-button');
    const measureButton = document.getElementById('measureButton');

    // 파일명 요소 생성
    let fileNameDisplay = document.createElement('div');
    fileNameDisplay.classList.add('file-name');

    // 이미지 업로드
    imageUpload.addEventListener('change', (event) => {
        const file = event.target.files[0];

        if (file) {
            // 1. 파일명 표시 및 UI 상태 변경
            fileNameDisplay.textContent = file.name;

            // 2. 기존 내용 제거 및 재배치
            let child = fileUploadArea.lastElementChild;
            while (child) {
                if (child !== fileSelectButton && child.id !== 'imageUpload') {
                    fileUploadArea.removeChild(child);
                }
                child = child.previousElementSibling;
            }

            fileUploadArea.appendChild(fileNameDisplay);
            fileUploadArea.appendChild(fileSelectButton);

            // 3. css 클래스 추가
            fileUploadArea.classList.add('file-selected');

            // 4. 퍼스널컬러 측정 버튼 활성화
            measureButton.classList.add('active');
            measureButton.disabled = false;
        } else {
            // 1. 기존 내용 생성 및 재배치
            fileUploadArea.classList.remove('file-selected');
            fileUploadArea.innerHTML = '';
            fileUploadArea.appendChild(fileSelectButton);

            // 2. 퍼스널컬러 측정 버튼 비활성화
            measureButton.classList.remove('active');
            measureButton.disabled = true;
        }
    });

    // 퍼스널컬러 측정
    measureButton.addEventListener('click', async () => {
        if (measureButton.disabled) {
            return;
        }

        const file = imageUpload.files[0];

        // 파일 검사
        if (!file) {
            alert('퍼스널컬러를 측정할 사진 파일을 선택해주세요.');
            return;
        }

        // request 생성
        const request = new FormData();
        request.append('image', file);

        // API 호출
        try {
            // 측정 버튼 비활성화
            measureButton.disabled = true;
            measureButton.classList.remove('active');
            measureButton.textContent = '퍼스널컬러 측정 중⋯'

            // api 호출
            const response = await fetch('/api/personal-color', {
                method: 'POST',
                body: request,
            });

            // 응답 데이터 SessionStorage 저장
            const result = await response.json();

            sessionStorage.setItem('personalColorResult', JSON.stringify(result));

            // 결과 페이지로 이동
            window.location.href = '/personal-color';
        } catch (error) {
            alert('측정 중 오류가 발생했습니다. 다시 시도해주세요.');
            console.log(error);

            // 측정 버튼 활성화
            measureButton.disabled = false;
            measureButton.classList.add('active');
            measureButton.textContent = '퍼스널컬러 측정하기';
        }
    });

});