const iconPaths = {
      food: '<path d="M6 3v8"/><path d="M10 3v8"/><path d="M8 3v18"/><path d="M15 3v18"/><path d="M15 3c3 2 3 6 0 8"/>',
      med: '<path d="M10 21h4"/><path d="M12 17v4"/><path d="M8 3h8v6H8z"/><path d="M5 9h14v8H5z"/><path d="M12 11v4"/><path d="M10 13h4"/>',
      syringe: '<path d="m18 2 4 4"/><path d="m17 7 2-2"/><path d="m2 22 7-7"/><path d="m9 15 6-6 3 3-6 6z"/><path d="m7 13 4 4"/>',
      pill: '<path d="M10 21a6.5 6.5 0 0 1-4.6-11.1l4.5-4.5a6.5 6.5 0 1 1 9.2 9.2l-4.5 4.5A6.5 6.5 0 0 1 10 21Z"/><path d="m8.5 8.5 7 7"/>',
      bottle: '<path d="M10 2h4"/><path d="M11 2v4l-3 4v10h8V10l-3-4V2"/><path d="M10 14h4"/>',
      drop: '<path d="M12 22a7 7 0 0 1-7-7c0-5 7-13 7-13s7 8 7 13a7 7 0 0 1-7 7Z"/>',
      poop: '<path d="M7 19h10a4 4 0 0 0 0-8 4.5 4.5 0 0 0-8.8-1.2A4.7 4.7 0 0 0 7 19Z"/><path d="M12 4c2 1 3 2 3 4"/>',
      walk: '<path d="M13 5a2 2 0 1 0 0-4 2 2 0 0 0 0 4Z"/><path d="m10 8 3-2 3 3"/><path d="m13 6-2 6 5 2"/><path d="m8 21 3-9"/><path d="m16 21-2-7"/>',
      bath: '<path d="M4 12h16v3a6 6 0 0 1-6 6h-4a6 6 0 0 1-6-6z"/><path d="M7 12V6a3 3 0 0 1 6 0"/><path d="M13 6h4"/>',
      heart: '<path d="M20.8 4.6a5.5 5.5 0 0 0-7.8 0L12 5.6l-1-1a5.5 5.5 0 1 0-7.8 7.8l1 1L12 21l7.8-7.6 1-1a5.5 5.5 0 0 0 0-7.8Z"/>'
    };
    const categories = [
      { key:'식사와 영양제', api:'FOOD', color:'#6f9ed8' },
      { key:'건강', api:'HEALTH', color:'#8ebd69' },
      { key:'활동', api:'ACTIVITY', color:'#9168ca' },
      { key:'미용/위생', api:'GROOMING', color:'#de8fa2' },
      { key:'배변', api:'POTTY', color:'#dcb85a' },
      { key:'증상', api:'SYMPTOM', color:'#a77964' }
    ];
    const categoryApi = key => categories.find(c => c.key === key)?.api || key;
    const iconApi = {
      food: 'FEED',
      med: 'WEIGHT',
      syringe: 'INJECTION',
      pill: 'PILL',
      bottle: 'OINTMENT',
      drop: 'WATER_DROP',
      poop: 'TOOTH',
      walk: 'WALK',
      bath: 'BATH',
      heart: 'PLAY'
    };
    const categoryFromApi = {
      FOOD: '식사와 영양제',
      HEALTH: '건강',
      ACTIVITY: '활동',
      GROOMING: '미용/위생',
      POTTY: '배변',
      SYMPTOM: '증상'
    };
    const iconFromApi = {
      FOOD_BOWL: 'food',
      FEED: 'food',
      WATER_DROP: 'drop',
      SUPPLEMENT: 'med',
      OMEGA3: 'pill',
      WEIGHT: 'med',
      INJECTION: 'syringe',
      PILL: 'pill',
      OINTMENT: 'bottle',
      BLOOD_TEST: 'drop',
      WALK: 'walk',
      LONG_WALK: 'walk',
      PLAY: 'heart',
      HIKING: 'walk',
      FRIENDS: 'heart',
      TOOTH: 'poop',
      GROOMING: 'bath',
      BATH: 'bath',
      EAR_CLEANING: 'bath',
      NAIL: 'bath',
      POOP: 'poop',
      PEE: 'drop',
      VOMIT: 'food',
      DIARRHEA: 'poop',
      COUGH: 'heart',
      ITCHING: 'heart'
    };
    const palette = {
      '식사와 영양제': ['#6f9ed8','#eaf3ff'], '건강': ['#8ebd69','#eef7e8'], '활동': ['#9168ca','#f2ecff'],
      '미용/위생': ['#de8fa2','#fff0f3'], '배변': ['#dcb85a','#fff7df'], '증상': ['#a77964','#fff0ea']
    };
