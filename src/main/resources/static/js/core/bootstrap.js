function renderAll() {
  renderHome();
  renderTracker();
  renderSettings();
  renderPresetManage();
  renderTrackerManage();
  renderPresetForm();
}

async function loadInitialData() {
  const petsResponse = await api('/pets?userId=1');

  const pets = normalizeApiList(petsResponse);

  state.pets = pets.map(p => ({
    id: p.id,
    name: p.name,
    species: p.species,
    birth: p.birthDate || 'unknown'
  }));

  state.currentPetId = state.pets[0]?.id || null;

  await loadPresets();
  await loadLogsForSelectedDate();
  await loadWeekLogsForTracker();

  renderAll();
}

async function loadPresets() {
  const presetsResponse = await api('/presets?userId=1');
  const presets = normalizeApiList(presetsResponse);

  state.presets = presets.map(p => ({
    id: p.id,
    name: p.name,
    category: categoryFromApi[p.category] || p.category,
    icon: iconFromApi[p.icon] || p.icon || 'heart',
    tracked: Boolean(p.tracked)
  }));
}

async function loadLogsForSelectedDate() {
  if (!state.currentPetId) {
    state.logs = [];
    return;
  }

  const date = getSelectedDateString();

  const logsResponse = await api(
    `/pets/${state.currentPetId}/care-logs?date=${date}`
  );

  const logs = normalizeApiList(logsResponse);

  state.logs = logs.map(l => {
    const dateString = (l.recordedAt || '').slice(0, 10) || date;

    return {
      id: l.id,
      petId: l.petId,
      presetId: l.presetId,
      dateString,
      day: Number(dateString.slice(8, 10)) || state.selectedDay,
      time: (l.recordedAt || '').slice(11, 16) || '',
      name: l.careName || l.name,
      category: categoryFromApi[l.category] || l.category,
      icon: iconFromApi[l.icon] || l.icon || 'heart',
      memo: l.memo || ''
    };
  });
}

async function loadWeekLogsForTracker() {
  if (!state.currentPetId) {
    state.weekLogs = [];
    return;
  }

  const weekDates = getTodayWeekDates();

  const results = await Promise.all(
    weekDates.map(item =>
      api(`/pets/${state.currentPetId}/care-logs?date=${item.dateString}`)
    )
  );

  state.weekLogs = results.flatMap((response, index) => {
    const logs = normalizeApiList(response);
    const fallbackDateString = weekDates[index].dateString;

    return logs.map(l => {
      const dateString = (l.recordedAt || '').slice(0, 10) || fallbackDateString;

      return {
        id: l.id,
        petId: l.petId,
        presetId: l.presetId,
        dateString,
        day: Number(dateString.slice(8, 10)),
        time: (l.recordedAt || '').slice(11, 16) || '',
        name: l.careName || l.name,
        category: categoryFromApi[l.category] || l.category,
        icon: iconFromApi[l.icon] || l.icon || 'heart',
        memo: l.memo || ''
      };
    });
  });
}
