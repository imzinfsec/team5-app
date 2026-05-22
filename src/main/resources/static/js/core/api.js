const API_BASE = '/api';

async function api(path, options = {}) {
  try {
    const res = await fetch(API_BASE + path, {
      headers: {
        'Content-Type': 'application/json',
        ...(options.headers || {})
      },
      ...options
    });

    if (!res.ok) {
      const errorText = await res.text();
      console.error('API error:', res.status, errorText);
      throw new Error(`API error: ${res.status}`);
    }

    const text = await res.text();

    if (!text) {
      return { ok: true };
    }

    return JSON.parse(text);
  } catch (e) {
    console.warn('API 연결 실패:', e);
    return null;
  }
}
