function renderSettings() {
  const currentPet = pet();

  if (!currentPet) {
    document.querySelector('#profileCard').innerHTML = `
      <div class="avatar">?</div>
      <div>
        <h2>반려동물 없음</h2>
        <p>반려동물을 먼저 추가해 주세요</p>
      </div>
    `;
  } else {
    const avatarContent = currentPet.imageUrl
      ? `<img src="${currentPet.imageUrl}" alt="${currentPet.name}" />`
      : currentPet.name.slice(0, 2);

    document.querySelector('#profileCard').innerHTML = `
      <div class="avatar">${avatarContent}</div>
      <div>
        <h2>${currentPet.name} <span style="color:var(--pink)">♥</span></h2>
        <p>${currentPet.species} · ${currentPet.birth || 'unknown'}</p>
      </div>
      <button class="profile-edit" id="editCurrentPet">수정</button>
    `;

    document.querySelector('#editCurrentPet').onclick = () => {
      openPetForm(state.currentPetId);
    };
  }

  document.querySelector('#petSwitch').innerHTML = state.pets.map(p => {
    const avatarContent = p.imageUrl
      ? `<img src="${p.imageUrl}" alt="${p.name}" />`
      : p.name.slice(0, 2);

    return `
      <button class="pet-card ${p.id === state.currentPetId ? 'active' : ''}" data-id="${p.id}">
        <span class="avatar">${avatarContent}</span>
        <span>${p.name}</span>
      </button>
    `;
  }).join('');

  document.querySelectorAll('.pet-card').forEach(button => {
    button.onclick = async () => {
      state.currentPetId = Number(button.dataset.id);
      await loadLogsForSelectedDate();
      await loadWeekLogsForTracker();
      show('settings');
    };
  });
}

function renderPresetManage() {
  document.querySelector('#presetManageList').innerHTML = categories.map(cat => {
    const list = state.presets.filter(p => p.category === cat.key);
    if (!list.length) return '';

    return `
      <div class="category-section">
        <h3>${cat.key}</h3>
        <div class="preset-grid">
          ${list.map(p => {
            const cc = colors(p.category);
            return `
              <button class="preset-tile" data-id="${p.id}">
                <span class="circle" style="--c:${cc.c};--t:${cc.t}">
                  ${icon(p.icon)}
                </span>
                <span>${p.name}</span>
              </button>
            `;
          }).join('')}
        </div>
      </div>
    `;
  }).join('');

  document.querySelectorAll('#presetManageList .preset-tile').forEach(button => {
    button.onclick = () => openPresetForm(Number(button.dataset.id));
  });
}

function renderTrackerManage() {
  document.querySelector('#trackerManageList').innerHTML = categories.map(cat => {
    const list = state.presets.filter(p => p.category === cat.key);
    if (!list.length) return '';

    return `
      <div class="category-section">
        <h3>${cat.key}</h3>
        <div class="preset-grid">
          ${list.map(p => {
            const cc = colors(p.category);
            return `
              <button class="preset-tile ${p.tracked ? 'active' : ''}" data-id="${p.id}">
                <span class="circle" style="--c:${cc.c};--t:${cc.t}">
                  ${icon(p.icon)}
                </span>
                <span>${p.name}</span>
              </button>
            `;
          }).join('')}
        </div>
      </div>
    `;
  }).join('');

  document.querySelectorAll('#trackerManageList .preset-tile').forEach(button => {
    button.onclick = () => {
      const preset = state.presets.find(p => p.id === Number(button.dataset.id));
      if (!preset) return;
      preset.tracked = !preset.tracked;
      renderTrackerManage();
    };
  });
}