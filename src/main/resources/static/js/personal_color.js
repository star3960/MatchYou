document.addEventListener('DOMContentLoaded', () => {
    // SessionStorage에서 데이터 로드
    const result = sessionStorage.getItem('personalColorResult');

    if (!result) {
        document.getElementById('no1-color-text').textContent = '측정 결과가 없습니다.';
        return;
    }

    try {
        const data = JSON.parse(result);    // 데이터 가져오기
        updateUI(data);     // UI 업데이트
        sessionStorage.removeItem('personalColorResult');   // SessionStorage에서 데이터 삭제
    } catch (error) {
        console.log(error);
        document.getElementById('no1-color-text').textContent = '측정 중 오류가 발생했습니다.';
    }

    // UI 업데이트 메서드
    function updateUI(data) {
        // 이모지 및 메인 퍼스널컬러 업데이트
        const no1Color = data.no1PersonalColor;     // 메인 퍼스널컬러
        const season = no1Color.substring(0, 1);    // 이모지 결정 요소

        let iconPath = '';
        switch (season) {
            case '봄':
                iconPath = '/asset/spring_icon.png';
                break;
            case '여':
                iconPath = '/asset/summer_icon.png';
                break;
            case '가':
                iconPath = '/asset/autumn_icon.png';
                break;
            case '겨':
                iconPath = '/asset/winter_icon.png';
                break;
        }

        const iconElement = document.getElementById('personal-color-icon');
        const textElement = document.getElementById('no1-color-text');

        iconElement.src = iconPath;
        textElement.textContent = no1Color;

        // 퍼센티지 바 설정
        const percentages = [
            { color: data.no1PersonalColor, percentage: data.no1Percentage },
            { color: data.no2PersonalColor, percentage: data.no2Percentage },
            { color: data.no3PersonalColor, percentage: data.no3Percentage },
        ];

        const barsContainer = document.getElementById('percentage-bars-container');
        barsContainer.innerHTML = '';

        percentages.forEach(item => {
            const barItem = document.createElement('div');
            barItem.className = 'bar-item';
            barItem.innerHTML = `
                <span class="color-label">${item.color}</span>
                <div class="bar-wrapper">
                    <div class="percentage-bar" style="width: ${item.percentage}%; background-color: #FF399F;"></div>
                </div>
                <span class="percentage-text">${item.percentage}%</span>
            `;
            barsContainer.appendChild(barItem);
        });

        // 측정 근거 설정
        document.getElementById('reason-content').textContent = data.reason;
    }
});