function addLogFromPreset(id) {
  if (!state.currentPetId) {
    alert('반려동물을 먼저 추가해 주세요.');
    return;
  }

  if (!isSelectedDateToday()) {
    alert('오늘 날짜에만 기록할 수 있어요.');
    return;
  }

  const p = state.presets.find(x => x.id === id);

  if (!p) {
    alert('프리셋을 찾을 수 없습니다.');
    return;
  }

  const date = getSelectedDateString();
  const time = getKoreaNowTimeString();
  const recordedAt = `${date}T${time}:00`;

  api(`/pets/${state.currentPetId}/care-logs/quick`, {
    method: 'POST',
    body: JSON.stringify({
      userId: 1,
      presetId: p.id,
      recordedAt
    })
  }).then(async created => {
    if (!created) {
      alert('기록 저장에 실패했어요.');
      return;
    }

    await loadLogsForSelectedDate();
    await loadWeekLogsForTracker();

    renderAll();
  });
}

function openLog(id) {
  const l = state.logs.find(x => x.id === id);

  if (!l) {
    alert('기록을 찾을 수 없습니다.');
    return;
  }

  state.editingLogId = id;

  document.querySelector('#logTitle').textContent = l.name;
  document.querySelector('#logTime').textContent =
    `${Number(l.dateString.slice(5, 7))}월 ${l.day}일 ${l.time}`;

  document.querySelector('#logMemo').value = l.memo || '';
  document.querySelector('#logTimeInput').value = l.time;
  document.querySelector('#logSheet').classList.add('active');
}

async function saveLog() {
  if (!state.editingLogId) return;

  if (!isSelectedDateToday()) {
    alert('오늘 날짜의 기록만 수정할 수 있어요.');
    return;
  }

  const memo = document.querySelector('#logMemo').value.trim();
  const time = document.querySelector('#logTimeInput').value;

  if (!time) {
    alert('시간을 입력해 주세요.');
    return;
  }

  const recordedAt = `${getSelectedDateString()}T${time}:00`;

  const updated = await api(`/care-logs/${state.editingLogId}`, {
    method: 'PUT',
    body: JSON.stringify({
      memo,
      recordedAt
    })
  });

  if (!updated) {
    alert('기록 수정에 실패했어요.');
    return;
  }

  document.querySelector('#logSheet').classList.remove('active');

  state.editingLogId = null;

  await loadLogsForSelectedDate();
  await loadWeekLogsForTracker();

  renderAll();
}

async function deleteLog() {
  if (!state.editingLogId) {
    alert('삭제할 기록을 찾을 수 없습니다.');
    return;
  }

  const ok = confirm('이 기록을 삭제할까요?');

  if (!ok) return;

  const deleted = await api(`/care-logs/${state.editingLogId}`, {
    method: 'DELETE'
  });

  if (!deleted) {
    alert('기록 삭제에 실패했어요. 서버 로그를 확인해 주세요.');
    return;
  }

  document.querySelector('#logSheet').classList.remove('active');

  state.editingLogId = null;

  await loadLogsForSelectedDate();
  await loadWeekLogsForTracker();

  renderAll();
}