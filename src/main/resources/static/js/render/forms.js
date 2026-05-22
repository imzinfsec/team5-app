function renderPresetForm() {
  document.querySelector('#categoryChips').innerHTML = categories.map(c => `
    <button
      class="chip ${state.selectedCategory === c.key ? 'active' : ''}"
      data-key="${c.key}"
      style="--c:${c.color}"
    >
      ${c.key}
    </button>
  `).join('');

  document.querySelectorAll('#categoryChips .chip').forEach(button => {
    button.onclick = () => {
      state.selectedCategory = button.dataset.key;
      renderPresetForm();
    };
  });

  const keys = Object.keys(iconPaths);

  document.querySelector('#iconGrid').innerHTML = keys.map((key, index) => `
    <button
      class="pick-icon ${state.selectedIcon === key ? 'active' : ''}"
      data-key="${key}"
      data-index="${index}"
    >
      ${icon(key)}
    </button>
  `).join('');

  document.querySelectorAll('#iconGrid .pick-icon').forEach(button => {
    button.onclick = () => {
      state.selectedIcon = button.dataset.key;

      document.querySelectorAll('#iconGrid .pick-icon').forEach(x => {
        x.classList.remove('active');
      });

      button.classList.add('active');
    };
  });

  const actions = document.querySelector('#presetActions');

  actions.classList.toggle('edit-mode', Boolean(state.editingPresetId));
  actions.classList.toggle('add-mode', !state.editingPresetId);

  document.querySelector('#deletePreset').style.display =
    state.editingPresetId ? 'block' : 'none';

  document.querySelector('#cancelPreset').style.display =
    state.editingPresetId ? 'none' : 'block';
}

function openPresetForm(id = null) {
  state.editingPresetId = id;

  const formTitle = document.querySelector('#presetFormTitle');

  if (id) {
    const p = state.presets.find(x => x.id === id);

    if (!p) {
      alert('프리셋을 찾을 수 없습니다.');
      return;
    }

    document.querySelector('#presetName').value = p.name;
    state.selectedCategory = p.category;
    state.selectedIcon = p.icon;
    formTitle.textContent = '프리셋 수정';
  } else {
    document.querySelector('#presetName').value = '';
    state.selectedCategory = '식사와 영양제';
    state.selectedIcon = 'bottle';
    formTitle.textContent = '프리셋 추가';
  }

  show('presetForm');
}

async function savePreset() {
  const name = document.querySelector('#presetName').value.trim() || '새 프리셋';

  const body = {
    userId: 1,
    name,
    category: categoryApi(state.selectedCategory),
    icon: iconApi[state.selectedIcon] || state.selectedIcon,
    color: colors(state.selectedCategory).c,
    sortOrder: state.presets.length + 1
  };

  if (state.editingPresetId) {
    const updated = await api(`/presets/${state.editingPresetId}`, {
      method: 'PUT',
      body: JSON.stringify(body)
    });

    if (!updated) {
      alert('프리셋 수정에 실패했어요.');
      return;
    }
  } else {
    const created = await api('/presets', {
      method: 'POST',
      body: JSON.stringify(body)
    });

    if (!created) {
      alert('프리셋 추가에 실패했어요.');
      return;
    }
  }

  state.editingPresetId = null;

  await loadPresets();
  await loadWeekLogsForTracker();

  show('presetManage');
}