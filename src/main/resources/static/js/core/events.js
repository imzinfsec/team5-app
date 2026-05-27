// js/core/events.js

// ================================
// 캘린더 주간/월간 전환
// ================================
document.querySelector('#weekBtn').onclick = () => {
  state.calendarMode = 'week';
  document.querySelector('#weekBtn').classList.add('active');
  document.querySelector('#monthBtn').classList.remove('active');
  renderHome();
};

document.querySelector('#monthBtn').onclick = () => {
  state.calendarMode = 'month';
  document.querySelector('#monthBtn').classList.add('active');
  document.querySelector('#weekBtn').classList.remove('active');
  renderHome();
};

// ================================
// 하단 네비게이션 / 뒤로가기 버튼
// ================================
document.querySelectorAll('[data-go]').forEach(button => {
  button.onclick = () => show(button.dataset.go);
});

// ================================
// 설정 메뉴 이동
// ================================
document.querySelector('#openPresetManage').onclick = () => show('presetManage');
document.querySelector('#openTrackerManage').onclick = () => show('trackerManage');
document.querySelector('#openPresetAdd').onclick = () => openPresetForm(null);
document.querySelector('#openPetAdd').onclick = () => openPetForm(null);

// ================================
// 주간 트래커 요소 저장
// ================================
document.querySelector('#saveTrackerItems').onclick = async () => {
  const presetIds = state.presets
    .filter(preset => preset.tracked)
    .map(preset => preset.id);

  const result = await api('/presets/tracking', {
    method: 'PUT',
    body: JSON.stringify({
      presetIds
    })
  });

  if (!result) {
    alert('주간 트래커 요소 저장에 실패했어요. 서버 로그를 확인해 주세요.');
    return;
  }

  await loadPresets();
  await loadWeekLogsForTracker();
  show('settings');
};

// ================================
// 프리셋 저장 / 취소 / 삭제
// ================================
document.querySelector('#savePreset').onclick = async () => await savePreset();

document.querySelector('#cancelPreset').onclick = () => show('presetManage');

document.querySelector('#deletePreset').onclick = async () => {
  if (!state.editingPresetId) {
    alert('삭제할 프리셋을 찾을 수 없습니다.');
    return;
  }

  if (!confirm('이 프리셋을 삭제할까요?')) return;

  const deleted = await api(`/presets/${state.editingPresetId}`, { method: 'DELETE' });

  if (!deleted) {
    alert('프리셋 삭제에 실패했어요. 서버 로그를 확인해 주세요.');
    return;
  }

  state.editingPresetId = null;
  await loadPresets();
  await loadWeekLogsForTracker();
  show('presetManage');
};

// ================================
// 반려동물 추가/수정 폼
// ================================
let selectedSpecies = '강아지';

function openPetForm(id = null) {
  state.editingPetId = id;

  const title = document.querySelector('#petFormTitle');
  const target = id ? state.pets.find(pet => pet.id === id) : null;

  title.textContent = id ? '반려동물 수정' : '반려동물 추가';

  document.querySelector('#petName').value = target?.name || '';
  document.querySelector('#petBirth').value =
    target && target.birth !== 'unknown' ? target.birth : '';
  document.querySelector('#birthUnknown').checked =
    !target || target.birth === 'unknown';
  document.querySelector('#petBirth').disabled =
    !target || target.birth === 'unknown';

  selectedSpecies = target?.species || '강아지';

  document.querySelectorAll('#petSpeciesChips .chip').forEach(chip => {
    chip.classList.toggle('active', chip.dataset.species === selectedSpecies);
  });

  // 이미지 미리보기 초기화
  const preview = document.querySelector('#petImgPreview');
  const placeholder = document.querySelector('#petImgPlaceholder');
  const input = document.querySelector('#petImageInput');

  input.value = '';

  if (target?.imageUrl) {
    preview.src = target.imageUrl;
    preview.style.display = 'block';
    placeholder.style.display = 'none';
  } else {
    preview.src = '';
    preview.style.display = 'none';
    placeholder.style.display = 'flex';
  }

  document.querySelector('#deletePet').style.display = id ? 'block' : 'none';

  show('petForm');
}

// 반려동물 종류 선택
document.querySelectorAll('#petSpeciesChips .chip').forEach(button => {
  button.onclick = () => {
    selectedSpecies = button.dataset.species;
    document.querySelectorAll('#petSpeciesChips .chip').forEach(chip => {
      chip.classList.toggle('active', chip === button);
    });
  };
});

// 생일 모름 체크
document.querySelector('#birthUnknown').onchange = event => {
  const checked = event.target.checked;
  document.querySelector('#petBirth').disabled = checked;
  if (checked) document.querySelector('#petBirth').value = '';
};

// 반려동물 저장 (이미지 업로드 포함)
document.querySelector('#savePet').onclick = async () => {
  const name = document.querySelector('#petName').value.trim() || '새친구';
  const species = selectedSpecies;
  const birth = document.querySelector('#birthUnknown').checked
    ? 'unknown'
    : (document.querySelector('#petBirth').value || 'unknown');

  let savedPetId = state.editingPetId;

  if (state.editingPetId) {
    // 수정
    const updated = await api(`/pets/${state.editingPetId}`, {
	  method: 'PUT',
	  body: JSON.stringify({
	    name,
	    species,
	    birthDate: birth === 'unknown' ? null : birth
	  })
	});

    if (!updated) {
      alert('반려동물 수정에 실패했어요. 서버 로그를 확인해 주세요.');
      return;
    }

    const target = state.pets.find(pet => pet.id === state.editingPetId);
    if (target) Object.assign(target, { name, species, birth });

  } else {
    // 추가
    const created = await api('/pets', {
	  method: 'POST',
	  body: JSON.stringify({
	    name,
	    species,
	    birthDate: birth === 'unknown' ? null : birth
	  })
	});

    if (!created) {
      alert('반려동물 추가에 실패했어요. 서버 로그를 확인해 주세요.');
      return;
    }

    savedPetId = created.id;

    state.pets.push({
      id: created.id,
      name: created.name,
      species: created.species,
      birth: created.birthDate || 'unknown',
      imageUrl: null
    });

    state.currentPetId = created.id;
  }

  // 이미지 파일이 선택된 경우 업로드
  const imageFile = document.querySelector('#petImageInput').files[0];

  if (imageFile && savedPetId) {
    const formData = new FormData();
    formData.append('image', imageFile);

    try {
      const res = await fetch(`/api/pets/${savedPetId}/image`, {
        method: 'POST',
        body: formData
      });

      if (res.ok) {
        const data = await res.json();
        // state.pets의 imageUrl 업데이트
        const target = state.pets.find(p => p.id === savedPetId);
        if (target) target.imageUrl = data.imageUrl;
      } else {
        console.warn('이미지 업로드 실패');
      }
    } catch (e) {
      console.warn('이미지 업로드 오류:', e);
    }
  }

  // 폼 초기화
  state.editingPetId = null;
  document.querySelector('#petName').value = '';
  document.querySelector('#petBirth').value = '';
  document.querySelector('#birthUnknown').checked = false;
  document.querySelector('#petBirth').disabled = false;
  document.querySelector('#petImageInput').value = '';
  document.querySelector('#petImgPreview').style.display = 'none';
  document.querySelector('#petImgPlaceholder').style.display = 'flex';

  await loadLogsForSelectedDate();
  await loadWeekLogsForTracker();
  show('settings');
};

// 반려동물 삭제
document.querySelector('#deletePet').onclick = async () => {
  if (!state.editingPetId) {
    alert('삭제할 반려동물을 찾을 수 없습니다.');
    return;
  }

  if (state.pets.length <= 1) {
    alert('반려동물은 최소 1마리 이상 필요해요.');
    return;
  }

  if (!confirm('이 반려동물을 삭제할까요?')) return;

  const deleted = await api(`/pets/${state.editingPetId}`, { method: 'DELETE' });

  if (!deleted) {
    alert('반려동물 삭제에 실패했어요. 서버 로그를 확인해 주세요.');
    return;
  }

  state.pets = state.pets.filter(pet => pet.id !== state.editingPetId);
  state.logs = state.logs.filter(log => log.petId !== state.editingPetId);
  state.weekLogs = state.weekLogs.filter(log => log.petId !== state.editingPetId);

  state.currentPetId = state.pets[0]?.id || null;
  state.editingPetId = null;

  await loadLogsForSelectedDate();
  await loadWeekLogsForTracker();
  show('settings');
};

// ================================
// 기록 상세 모달
// ================================
document.querySelector('#closeLog').onclick = () => {
  document.querySelector('#logSheet').classList.remove('active');
};

document.querySelector('#saveLog').onclick = async () => await saveLog();

document.querySelector('#deleteLog').onclick = async () => await deleteLog();

document.querySelector('#logSheet').onclick = event => {
  if (event.target.id === 'logSheet') {
    event.currentTarget.classList.remove('active');
  }
};

// ================================
// 홈 프리셋 좌우 스크롤
// ================================
document.querySelector('#presetPrev').onclick = () => {
  document.querySelector('#homePresets').scrollBy({ left: -260, behavior: 'smooth' });
};

document.querySelector('#presetNext').onclick = () => {
  document.querySelector('#homePresets').scrollBy({ left: 260, behavior: 'smooth' });
};