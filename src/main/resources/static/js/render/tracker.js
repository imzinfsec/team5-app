function renderTracker() {
  document.querySelector('#trackerPetTitle').textContent =
    `${pet()?.name || '반려동물'} 루틴`;

  const weekRangeText = getWeekRangeText();

  document.querySelector('#trackerRange').textContent = weekRangeText;
  document.querySelector('#trackerHeroRange').textContent = weekRangeText;

  const tracked = state.presets.filter(p => p.tracked);
  const weekDates = getTodayWeekDates();

  document.querySelector('#miniWeek').innerHTML =
    '<b>항목</b>' +
    ['M', 'T', 'W', 'T', 'F', 'S', 'S']
      .map(d => `<b>${d}</b>`)
      .join('') +
    tracked.map(p => {
      const cells = weekDates.map(dayInfo =>
        state.weekLogs.some(log =>
          log.petId === state.currentPetId &&
          log.dateString === dayInfo.dateString &&
          log.presetId === p.id
        )
      );

      return `
        <span>${p.name}</span>
        ${cells.map(done => `<span class="box ${done ? 'done' : ''}"></span>`).join('')}
      `;
    }).join('');
}