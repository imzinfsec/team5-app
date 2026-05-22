const state = {
  pets: [],
  currentPetId: null,
  editingPetId: null,

  selectedDate: getInitialKoreaDateString(),
  selectedDay: Number(getInitialKoreaDateString().slice(8, 10)),

  calendarMode: 'week',

  editingPresetId: null,
  editingLogId: null,

  selectedCategory: '식사와 영양제',
  selectedIcon: 'bottle',

  presets: [],
  logs: [],

  // Weekly Tracker는 선택된 하루 기록이 아니라
  // 오늘이 속한 주간 전체 기록을 따로 담아서 계산한다.
  weekLogs: []
};

function getInitialKoreaDateString() {
  const now = new Date();
  const koreaDate = new Date(now.getTime() + 9 * 60 * 60 * 1000);

  const year = koreaDate.getUTCFullYear();
  const month = String(koreaDate.getUTCMonth() + 1).padStart(2, '0');
  const day = String(koreaDate.getUTCDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
}