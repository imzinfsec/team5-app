function renderDates(target, mode = state.calendarMode) {
  const el = document.querySelector(target);

  const days = mode === 'week'
    ? getSelectedWeekDates()
    : getCurrentMonthCalendarDates();

  el.innerHTML = days.map(dayInfo => {
    const has = state.logs.some(
      l => l.petId === state.currentPetId && l.dateString === dayInfo.dateString
    );

    return `
      <button
        class="date ${state.selectedDate === dayInfo.dateString ? 'selected' : ''} ${has ? 'has' : ''} ${dayInfo.dim ? 'dim' : ''}"
        data-date="${dayInfo.dateString}"
      >
        <span>${dayInfo.day}</span>
      </button>
    `;
  }).join('');

  el.querySelectorAll('.date').forEach(btn => {
    btn.onclick = async () => {
      state.selectedDate = btn.dataset.date;
      state.selectedDay = Number(state.selectedDate.slice(8, 10));

      await loadLogsForSelectedDate();

      renderAll();
    };
  });
}

function renderHome() {
  const currentPet = pet();

  document.querySelector('#homeSubtitle').textContent = currentPet
    ? `${currentPet.name}의 하루를 기록해요`
    : '반려동물을 먼저 추가해 주세요';

  document.querySelector('#selectedPetBtn').textContent = currentPet
    ? currentPet.name
    : '선택 없음';

  document.querySelector('.month').textContent = getKoreaCurrentYearMonthText();

  renderDates('#dates');

  // 주의: 메인 페이지 프리셋 유지
  document.querySelector('#homePresets').innerHTML = state.presets.map(p => {
    const cc = colors(p.category);

    return `
      <button class="preset" data-id="${p.id}" style="--c:${cc.c};--t:${cc.t}">
        <span class="circle">${icon(p.icon)}</span>
        <span>${p.name}</span>
      </button>
    `;
  }).join('');

  document.querySelectorAll('#homePresets .preset').forEach(btn => {
    btn.onclick = () => addLogFromPreset(Number(btn.dataset.id));
  });

  const logs = currentLogs();

  document.querySelector('#timelineTitle').textContent =
    `${Number(state.selectedDate.slice(5, 7))}월 ${state.selectedDay}일 기록`;

  document.querySelector('#logCount').textContent = `${logs.length}개`;

  document.querySelector('#timeline').innerHTML = logs.length
    ? logs.map(l => {
        const cc = colors(l.category);

        return `
          <button class="log" data-id="${l.id}">
            <span class="log-time">${l.time}</span>
            <span class="log-icon" style="--c:${cc.c};--t:${cc.t}">${icon(l.icon)}</span>
            <span>
              <strong>${l.name}</strong>
              <p>${l.memo || '메모 없음'}</p>
            </span>
            <span></span>
          </button>
        `;
      }).join('')
    : `<div class="empty">아직 기록이 없어요.<br>위 프리셋을 누르면 바로 기록돼요.</div>`;

  document.querySelectorAll('#timeline .log').forEach(btn => {
    btn.onclick = () => openLog(Number(btn.dataset.id));
  });
}
