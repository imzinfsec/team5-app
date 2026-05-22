function icon(key) {
  return `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor">${iconPaths[key] || iconPaths.heart}</svg>`;
}

function pet() {
  return state.pets.find(p => p.id === state.currentPetId) || null;
}

function colors(category) {
  const p = palette[category] || ['#8ebd69', '#eef7e8'];
  return { c: p[0], t: p[1] };
}

function currentLogs() {
  return state.logs
    .filter(l => l.petId === state.currentPetId && l.dateString === state.selectedDate)
    .sort((a, b) => b.time.localeCompare(a.time));
}

function show(id) {
  document.querySelectorAll('.page').forEach(p => {
    p.classList.toggle('active', p.id === id);
  });

  document.querySelectorAll('.nav').forEach(n => {
    n.classList.toggle('active', n.dataset.go === id);
  });

  document.querySelector('#bottomNav').style.display =
    ['presetManage', 'trackerManage', 'presetForm', 'petForm'].includes(id)
      ? 'none'
      : 'flex';

  renderAll();
}

function normalizeApiList(value) {
  if (Array.isArray(value)) return value;
  if (Array.isArray(value?.data)) return value.data;
  if (Array.isArray(value?.content)) return value.content;
  if (Array.isArray(value?.items)) return value.items;
  return [];
}

// ================================
// 한국 날짜/시간 유틸
// ================================
function getKoreaDateParts(date = new Date()) {
  const koreaDate = new Date(date.getTime() + 9 * 60 * 60 * 1000);

  return {
    year: koreaDate.getUTCFullYear(),
    month: koreaDate.getUTCMonth() + 1,
    day: koreaDate.getUTCDate(),
    hour: koreaDate.getUTCHours(),
    minute: koreaDate.getUTCMinutes()
  };
}

function getKoreaTodayString() {
  const { year, month, day } = getKoreaDateParts();

  return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
}

function getKoreaNowTimeString() {
  const { hour, minute } = getKoreaDateParts();

  return `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
}

function getSelectedDateString() {
  return state.selectedDate;
}

function isSelectedDateToday() {
  return state.selectedDate === getKoreaTodayString();
}

function parseDate(dateString) {
  return new Date(`${dateString}T00:00:00`);
}

function formatDateString(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
}

function addDays(date, amount) {
  const result = new Date(date);
  result.setDate(result.getDate() + amount);
  return result;
}

// 오늘 날짜가 속한 월~일 주간 계산
function getTodayWeekDates() {
  const today = parseDate(getKoreaTodayString());
  const dayOfWeek = today.getDay(); // 일:0, 월:1
  const diffToMonday = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
  const monday = addDays(today, diffToMonday);

  return Array.from({ length: 7 }, (_, index) => {
    const date = addDays(monday, index);

    return {
      day: date.getDate(),
      month: date.getMonth() + 1,
      dateString: formatDateString(date)
    };
  });
}

// 선택된 날짜가 속한 주간 계산
function getSelectedWeekDates() {
  const selected = parseDate(state.selectedDate);
  const dayOfWeek = selected.getDay();
  const diffToMonday = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
  const monday = addDays(selected, diffToMonday);

  return Array.from({ length: 7 }, (_, index) => {
    const date = addDays(monday, index);

    return {
      day: date.getDate(),
      month: date.getMonth() + 1,
      dateString: formatDateString(date)
    };
  });
}

function getCurrentMonthCalendarDates() {
  const today = parseDate(getKoreaTodayString());
  const year = today.getFullYear();
  const month = today.getMonth();

  const firstDate = new Date(year, month, 1);
  const firstDayOfWeek = firstDate.getDay();
  const diffToMonday = firstDayOfWeek === 0 ? -6 : 1 - firstDayOfWeek;
  const calendarStart = addDays(firstDate, diffToMonday);

  return Array.from({ length: 42 }, (_, index) => {
    const date = addDays(calendarStart, index);

    return {
      day: date.getDate(),
      month: date.getMonth() + 1,
      dateString: formatDateString(date),
      dim: date.getMonth() !== month
    };
  });
}

function getKoreaCurrentYearMonthText() {
  const { year, month } = getKoreaDateParts();

  return `${year}년 ${String(month).padStart(2, '0')}월`;
}

function getKoreaCurrentMonth() {
  return getKoreaDateParts().month;
}

function getWeekRangeText() {
  const weekDates = getTodayWeekDates();
  const start = weekDates[0].dateString.slice(5).replace('-', '.');
  const end = weekDates[6].dateString.slice(5).replace('-', '.');

  return `${start} - ${end}`;
}
