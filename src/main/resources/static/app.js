/* =========================================================================
   SkyDesk — Airline Operations Console
   Single-file vanilla JS frontend for the Spring Boot airline-reservation API.
   Served from /static, so all calls are same-origin relative paths.
   ========================================================================= */

const API_BASE = ''; // same origin — backend serves this file from /static

/* -------------------------------------------------------------------------
   Generic API client
   ------------------------------------------------------------------------- */
const Api = {
    async request(method, path, body) {
        const opts = { method, headers: {} };
        if (body !== undefined) {
            opts.headers['Content-Type'] = 'application/json';
            opts.body = JSON.stringify(body);
        }
        let res;
        try {
            res = await fetch(API_BASE + path, opts);
        } catch (networkErr) {
            throw new ApiError('Could not reach the server. Check that the backend is running.', 0);
        }
        const text = await res.text();
        let data = null;
        if (text) {
            try { data = JSON.parse(text); } catch { data = text; }
        }
        if (!res.ok) {
            const msg = (typeof data === 'string' && data) ? data : (data && data.message) || `Request failed (${res.status})`;
            throw new ApiError(msg, res.status, data);
        }
        return data;
    },
    get(path) { return this.request('GET', path); },
    post(path, body) { return this.request('POST', path, body); },
    put(path, body) { return this.request('PUT', path, body); },
    del(path) { return this.request('DELETE', path); },
};

class ApiError extends Error {
    constructor(message, status, data) {
        super(message);
        this.status = status;
        this.data = data;
    }
}

/* -------------------------------------------------------------------------
   Small DOM / formatting helpers
   ------------------------------------------------------------------------- */
function $(sel, root = document) { return root.querySelector(sel); }
function $all(sel, root = document) { return Array.from(root.querySelectorAll(sel)); }
function el(html) {
    const t = document.createElement('template');
    t.innerHTML = html.trim();
    return t.content.firstElementChild;
}
function esc(str) {
    if (str === null || str === undefined) return '';
    return String(str).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
}
function fmtDateTime(iso) {
    if (!iso) return '—';
    const d = new Date(iso);
    if (isNaN(d.getTime())) return esc(iso);
    return d.toLocaleString(undefined, { month: 'short', day: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}
function fmtDate(iso) {
    if (!iso) return '—';
    const d = new Date(iso);
    if (isNaN(d.getTime())) return esc(iso);
    return d.toLocaleDateString(undefined, { month: 'short', day: '2-digit', year: 'numeric' });
}
function fmtMoney(v) {
    if (v === null || v === undefined || v === '') return '—';
    const n = Number(v);
    if (isNaN(n)) return esc(v);
    return '₹' + n.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}
function toDatetimeLocal(iso) {
    if (!iso) return '';
    const d = new Date(iso);
    if (isNaN(d.getTime())) return '';
    const pad = n => String(n).padStart(2, '0');
    return `${d.getFullYear()}-${pad(d.getMonth()+1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
}
function pillClass(status) {
    return 'pill pill-' + String(status || '').toLowerCase();
}
function debounce(fn, ms) {
    let t;
    return (...args) => { clearTimeout(t); t = setTimeout(() => fn(...args), ms); };
}

/* -------------------------------------------------------------------------
   Toasts
   ------------------------------------------------------------------------- */
const ICONS = {
    check: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M8 12l3 3 5-6"/></svg>',
    alert: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 8v5M12 16h.01"/></svg>',
};
function toast(title, message, type = 'success') {
    const stack = $('#toastStack');
    const node = el(`
    <div class="toast ${type === 'error' ? 'error' : ''}">
      ${type === 'error' ? ICONS.alert : ICONS.check}
      <div>
        <div class="t-title">${esc(title)}</div>
        ${message ? `<div class="t-msg">${esc(message)}</div>` : ''}
      </div>
    </div>`);
    stack.appendChild(node);
    setTimeout(() => {
        node.style.transition = 'opacity .25s ease, transform .25s ease';
        node.style.opacity = '0';
        node.style.transform = 'translateX(12px)';
        setTimeout(() => node.remove(), 260);
    }, 4200);
}
function toastError(err, fallbackTitle = 'Something went wrong') {
    const message = err instanceof Error ? err.message : String(err);
    toast(fallbackTitle, message, 'error');
}

/* -------------------------------------------------------------------------
   Modal system
   ------------------------------------------------------------------------- */
const overlay = $('#overlay');
const modalRoot = $('#modal');

function openModal(innerHtml, { wide = false } = {}) {
    modalRoot.className = 'modal' + (wide ? ' wide' : '');
    modalRoot.innerHTML = innerHtml;
    overlay.classList.add('active');
    document.body.style.overflow = 'hidden';
    const first = modalRoot.querySelector('input, select, textarea');
    if (first) setTimeout(() => first.focus(), 60);
}
function closeModal() {
    overlay.classList.remove('active');
    modalRoot.innerHTML = '';
    document.body.style.overflow = '';
}
overlay.addEventListener('click', (e) => { if (e.target === overlay) closeModal(); });
document.addEventListener('keydown', (e) => { if (e.key === 'Escape' && overlay.classList.contains('active')) closeModal(); });

function confirmDialog({ title, message, sub, confirmLabel = 'Delete', danger = true, onConfirm }) {
    openModal(`
    <div class="modal-head">
      <h3>${esc(title)}</h3>
      <button class="close-x" data-close>${ICONS_X}</button>
    </div>
    <div class="confirm-body">
      <p>${esc(message)}</p>
      ${sub ? `<div class="sub">${esc(sub)}</div>` : ''}
    </div>
    <div class="modal-foot">
      <button class="btn btn-ghost" data-close>Cancel</button>
      <button class="btn ${danger ? 'btn-danger' : 'btn-primary'}" id="confirmBtn" style="${danger ? 'background:var(--red);color:#fff;' : ''}">${esc(confirmLabel)}</button>
    </div>
  `);
    modalRoot.parentElement.classList.add('active');
    modalRoot.classList.add('confirm-box');
    $all('[data-close]', modalRoot).forEach(b => b.addEventListener('click', closeModal));
    $('#confirmBtn', modalRoot).addEventListener('click', async () => {
        const btn = $('#confirmBtn', modalRoot);
        btn.disabled = true;
        btn.textContent = 'Working…';
        try {
            await onConfirm();
            closeModal();
        } catch (err) {
            toastError(err, 'Could not complete action');
            btn.disabled = false;
            btn.textContent = confirmLabel;
        }
    });
}
const ICONS_X = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 6L6 18M6 6l12 12"/></svg>';

/* -------------------------------------------------------------------------
   Router
   ------------------------------------------------------------------------- */
const Router = {
    current: 'dashboard',
    go(view) {
        this.current = view;
        $all('.nav-item').forEach(b => b.classList.toggle('active', b.dataset.view === view));
        $all('.view').forEach(v => v.classList.toggle('active', v.id === 'view-' + view));
        const meta = VIEW_META[view] || { title: view, sub: '' };
        $('#viewTitle').textContent = meta.title;
        $('#viewSub').textContent = meta.sub;
        renderTopbarActions(view);
        closeSidebarMobile();
        if (Views[view] && Views[view].onEnter) Views[view].onEnter();
    }
};

const VIEW_META = {
    dashboard: { title: 'Dashboard', sub: 'Live operational snapshot' },
    flights: { title: 'Flights', sub: 'Schedule, routes and status' },
    airports: { title: 'Airports', sub: 'Stations in the network' },
    aircraft: { title: 'Aircraft', sub: 'Fleet registry' },
    seats: { title: 'Seats', sub: 'Seat maps per aircraft' },
    flightseats: { title: 'Flight Seats', sub: 'Seat inventory per flight' },
    bookings: { title: 'Bookings', sub: 'Reservations and their lifecycle' },
    passengers: { title: 'Passengers', sub: 'Travelers attached to bookings' },
    tickets: { title: 'Tickets', sub: 'Issued tickets' },
    payments: { title: 'Payments', sub: 'Transactions and settlement' },
    users: { title: 'Users', sub: 'Accounts and roles' },
    notifications: { title: 'Notifications', sub: 'Messages sent to users' },
};

function renderTopbarActions(view) {
    const box = $('#topbarActions');
    box.innerHTML = '';
    const v = Views[view];
    if (v && v.topbarActions) box.appendChild(el(v.topbarActions()));
}

$all('.nav-item').forEach(btn => btn.addEventListener('click', () => Router.go(btn.dataset.view)));

/* Mobile sidebar */
const sidebar = $('#sidebar');
const scrim = $('#sidebarScrim');
$('#menuToggle').addEventListener('click', () => { sidebar.classList.add('open'); scrim.classList.add('active'); });
scrim.addEventListener('click', closeSidebarMobile);
function closeSidebarMobile() { sidebar.classList.remove('open'); scrim.classList.remove('active'); }

/* -------------------------------------------------------------------------
   Sidebar entity counts + API status
   ------------------------------------------------------------------------- */
async function refreshSidebarCounts() {
    const map = {
        flights: '/flights', airports: '/airports', aircraft: '/aircrafts', seats: '/seats',
        flightseats: '/flightseats', bookings: '/bookings', passengers: '/passengers',
        tickets: '/tickets', payments: '/payments', users: '/users', notifications: '/notifications',
    };
    await Promise.all(Object.entries(map).map(async ([key, path]) => {
        const node = $('#cnt-' + key);
        try {
            const data = await Api.get(path);
            node.textContent = Array.isArray(data) ? data.length : '–';
        } catch {
            node.textContent = '–';
        }
    }));
}

async function checkApiStatus() {
    const dot = $('#api-status-dot');
    const text = $('#api-status-text');
    try {
        await Api.get('/airports');
        dot.classList.remove('off');
        text.textContent = 'API connected · :8080';
    } catch {
        dot.classList.add('off');
        text.textContent = 'API unreachable';
    }
}

/* -------------------------------------------------------------------------
   View registry — each entry can define onEnter() and topbarActions()
   ------------------------------------------------------------------------- */
const Views = {};

function buildAllViews() {
    const content = $('#content');
    Object.keys(VIEW_META).forEach(key => {
        content.appendChild(el(`<section class="view" id="view-${key}"></section>`));
    });
    registerDashboardView();
    registerAirportsView();
    registerAircraftView();
    registerSeatsView();
    registerFlightsView();
    registerFlightSeatsView();
    registerBookingsView();
    registerPassengersView();
    registerTicketsView();
    registerPaymentsView();
    registerUsersView();
    registerNotificationsView();
}

/* =========================================================================
   DASHBOARD
   ========================================================================= */
function registerDashboardView() {
    const root = $('#view-dashboard');
    root.innerHTML = `
    <div class="stat-grid" id="dashStats">
      ${Array.from({length:4}).map(() => `
        <div class="stat-card"><div class="skeleton" style="width:60%"></div><div class="skeleton" style="width:40%;height:26px;margin-top:10px"></div></div>
      `).join('')}
    </div>
    <div class="flap-board">
      <div class="flap-board-head">
        <h3>Departures Board</h3>
        <span class="hint" id="flapHint">loading schedule…</span>
      </div>
      <div class="table-wrap">
        <table class="flap-table">
          <thead><tr><th>Flight</th><th>Route</th><th>Departs</th><th>Arrives</th><th>Fare</th><th>Status</th></tr></thead>
          <tbody id="flapBody"></tbody>
        </table>
      </div>
    </div>
  `;

    Views.dashboard = {
        onEnter: loadDashboard,
    };
}

async function loadDashboard() {
    const statsBox = $('#dashStats');
    try {
        const d = await Api.get('/dashboard/statistics');
        const cards = [
            { label: 'Total Flights', value: d.totalFlights, sub: 'in schedule', tint: 'rgba(232,163,61,.1)' },
            { label: 'Total Bookings', value: d.totalBookings, sub: `${d.todayBookings ?? 0} today`, tint: 'rgba(47,158,143,.1)' },
            { label: 'Confirmed / Cancelled', value: `${d.confirmedBookings ?? 0} / ${d.cancelledBookings ?? 0}`, sub: 'booking outcomes', tint: 'rgba(58,110,209,.08)' },
            { label: 'Available Seats', value: d.availableSeats, sub: `Revenue ${fmtMoney(d.totalRevenue)}`, tint: 'rgba(209,72,58,.08)' },
        ];
        statsBox.innerHTML = cards.map(c => `
      <div class="stat-card" style="--stat-tint:${c.tint}">
        <div class="stat-label">${esc(c.label)}</div>
        <div class="stat-value">${c.value ?? '—'}</div>
        <div class="stat-sub">${esc(c.sub)}</div>
      </div>
    `).join('');
    } catch (err) {
        statsBox.innerHTML = `<div class="help-banner error" style="grid-column:1/-1">${ICONS.alert}<div>Could not load dashboard statistics. ${esc(err.message)}</div></div>`;
    }

    const flapBody = $('#flapBody');
    const flapHint = $('#flapHint');
    try {
        const [flights, airports] = await Promise.all([Api.get('/flights'), Api.get('/airports')]);
        const airportMap = Object.fromEntries(airports.map(a => [a.id, a.airportCode || a.city]));
        flapHint.textContent = `${flights.length} flight${flights.length === 1 ? '' : 's'} on file`;
        if (!flights.length) {
            flapBody.innerHTML = `<tr><td colspan="6" style="color:rgba(246,243,236,.5);font-family:var(--font-body);padding:24px;text-align:center;">No flights scheduled yet — add one from the Flights section.</td></tr>`;
            return;
        }
        flapBody.innerHTML = flights.slice(0, 12).map(f => `
      <tr>
        <td class="flap-num">${esc(f.flightNumber)}</td>
        <td>${esc(airportMap[f.sourceAirportId] || f.sourceAirportId)} → ${esc(airportMap[f.destinationAirportId] || f.destinationAirportId)}</td>
        <td>${fmtDateTime(f.departureTime)}</td>
        <td>${fmtDateTime(f.arrivalTime)}</td>
        <td>${fmtMoney(f.basePrice)}</td>
        <td><span class="${pillClass(f.status)}">${esc(f.status)}</span></td>
      </tr>
    `).join('');
    } catch (err) {
        flapHint.textContent = 'failed to load';
        flapBody.innerHTML = `<tr><td colspan="6" style="color:#f2887c;font-family:var(--font-body);padding:20px;">Could not load flights: ${esc(err.message)}</td></tr>`;
    }
}

/* =========================================================================
   GENERIC CRUD VIEW FACTORY
   Each entity view: a panel with search + "Add" button, a data table,
   row actions (edit/delete), and a modal form built from a field schema.
   ========================================================================= */
function makeCrudView(cfg) {
    // cfg: { key, path, title, icon, columns:[{key,label,render?}], fields:[...],
    //        emptyText, idField='id', extraRowActions?(row)->htmlString,
    //        afterSave?(), searchFields:[...] }
    const root = $('#view-' + cfg.key);
    const idField = cfg.idField || 'id';
    let rows = [];
    let filterText = '';

    root.innerHTML = `
    <div class="panel">
      <div class="panel-head">
        <div>
          <h3>${esc(cfg.title)}</h3>
          <div class="meta" id="${cfg.key}-meta">—</div>
        </div>
        <div class="panel-actions">
          <div class="search-box">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="7"/><path d="M21 21l-4.3-4.3"/></svg>
            <input type="text" placeholder="Search…" id="${cfg.key}-search">
          </div>
          <button class="btn btn-ghost btn-sm" id="${cfg.key}-refresh">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 12a9 9 0 11-3-6.7M21 4v6h-6"/></svg>
            Refresh
          </button>
          <button class="btn btn-amber btn-sm" id="${cfg.key}-add">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.4"><path d="M12 5v14M5 12h14"/></svg>
            Add ${esc(cfg.singular || cfg.title)}
          </button>
        </div>
      </div>
      <div class="panel-body" style="padding:0;">
        <div class="table-wrap">
          <table class="data">
            <thead><tr>${cfg.columns.map(c => `<th>${esc(c.label)}</th>`).join('')}<th style="text-align:right;">Actions</th></tr></thead>
            <tbody id="${cfg.key}-tbody">
              ${skeletonRows(cfg.columns.length + 1)}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `;

    function skeletonRows(cols) {
        return Array.from({length: 4}).map(() => `
      <tr class="skeleton-row">${Array.from({length: cols}).map(() => `<td><div class="skeleton" style="width:${40+Math.random()*40}%"></div></td>`).join('')}</tr>
    `).join('');
    }

    function applyFilter() {
        if (!filterText) return rows;
        const f = filterText.toLowerCase();
        const fields = cfg.searchFields || cfg.columns.map(c => c.key);
        return rows.filter(r => fields.some(k => String(r[k] ?? '').toLowerCase().includes(f)));
    }

    function renderTable() {
        const tbody = $('#' + cfg.key + '-tbody');
        const data = applyFilter();
        $('#' + cfg.key + '-meta').textContent = `${rows.length} record${rows.length === 1 ? '' : 's'}${filterText ? ` · ${data.length} matching` : ''}`;
        if (!rows.length) {
            tbody.innerHTML = `<tr><td colspan="${cfg.columns.length + 1}">
        <div class="empty-state">
          ${ICONS.alert}
          <p>${esc(cfg.emptyText || `No ${cfg.title.toLowerCase()} yet.`)}</p>
          <div class="sub">Use “Add ${esc(cfg.singular || cfg.title)}” to create the first one.</div>
        </div>
      </td></tr>`;
            return;
        }
        if (!data.length) {
            tbody.innerHTML = `<tr><td colspan="${cfg.columns.length + 1}"><div class="empty-state"><p>No matches for “${esc(filterText)}”.</p></div></td></tr>`;
            return;
        }
        tbody.innerHTML = data.map(row => `
      <tr>
        ${cfg.columns.map(c => `<td>${c.render ? c.render(row) : esc(row[c.key])}</td>`).join('')}
        <td>
          <div class="row-actions" style="justify-content:flex-end;">
            ${cfg.extraRowActions ? cfg.extraRowActions(row) : ''}
            <button class="icon-btn" data-edit="${row[idField]}" title="Edit">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 20h9"/><path d="M16.5 3.5a2.1 2.1 0 013 3L7 19l-4 1 1-4z"/></svg>
            </button>
            <button class="icon-btn danger" data-delete="${row[idField]}" title="Delete">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 6h18M8 6V4a1 1 0 011-1h6a1 1 0 011 1v2m2 0l-1 14a1 1 0 01-1 1H7a1 1 0 01-1-1L5 6"/></svg>
            </button>
          </div>
        </td>
      </tr>
    `).join('');

        $all('[data-edit]', tbody).forEach(btn => btn.addEventListener('click', () => {
            const row = rows.find(r => String(r[idField]) === btn.dataset.edit);
            openFormModal(cfg, row);
        }));
        $all('[data-delete]', tbody).forEach(btn => btn.addEventListener('click', () => {
            const row = rows.find(r => String(r[idField]) === btn.dataset.delete);
            confirmDialog({
                title: `Delete ${cfg.singular || cfg.title}?`,
                message: `This will permanently remove ${cfg.describeRow ? cfg.describeRow(row) : 'this record'}.`,
                sub: 'This action cannot be undone.',
                confirmLabel: 'Delete',
                onConfirm: async () => {
                    await Api.del(`${cfg.path}/${row[idField]}`);
                    toast('Deleted', `${cfg.singular || cfg.title} removed successfully.`);
                    await load();
                    refreshSidebarCounts();
                }
            });
        }));
    }

    async function load() {
        try {
            rows = await Api.get(cfg.path);
            renderTable();
        } catch (err) {
            $('#' + cfg.key + '-tbody').innerHTML = `<tr><td colspan="${cfg.columns.length + 1}"><div class="help-banner error">${ICONS.alert}<div>Could not load data: ${esc(err.message)}</div></div></td></tr>`;
        }
    }

    $('#' + cfg.key + '-add').addEventListener('click', () => openFormModal(cfg, null));
    $('#' + cfg.key + '-refresh').addEventListener('click', () => { load(); refreshSidebarCounts(); });
    $('#' + cfg.key + '-search').addEventListener('input', debounce((e) => { filterText = e.target.value.trim(); renderTable(); }, 180));

    Views[cfg.key] = {
        onEnter: load,
        topbarActions: cfg.topbarActions,
        reload: load,
    };

    return { load };
}

/* -------------------------------------------------------------------------
   Generic form modal — builds inputs from a field schema, validates
   required fields client-side, posts/puts, shows server error messages.
   ------------------------------------------------------------------------- */
function openFormModal(cfg, existing) {
    const isEdit = !!existing;
    const idField = cfg.idField || 'id';
    const fieldsHtml = cfg.fields.map(f => fieldHtml(f, existing)).join('');

    openModal(`
    <div class="modal-head">
      <h3>${isEdit ? 'Edit' : 'Add'} ${esc(cfg.singular || cfg.title)}</h3>
      <button class="close-x" data-close>${ICONS_X}</button>
    </div>
    <div class="modal-body">
      <div class="help-banner error" id="formErrBanner" style="display:none;"></div>
      <form id="entityForm">
        <div class="form-grid">${fieldsHtml}</div>
      </form>
    </div>
    <div class="modal-foot">
      <button class="btn btn-ghost" data-close>Cancel</button>
      <button class="btn btn-primary" id="saveBtn">${isEdit ? 'Save changes' : 'Create'}</button>
    </div>
  `);

    $all('[data-close]', modalRoot).forEach(b => b.addEventListener('click', closeModal));

    // populate dynamic select options if provided async
    cfg.fields.forEach(f => {
        if ((f.type === 'select' || f.type === 'select-number') && typeof f.options === 'function') {
            const sel = $(`[name="${f.name}"]`, modalRoot);
            if (!sel) return;
            f.options().then(opts => {
                const current = existing ? existing[f.name] : null;
                sel.innerHTML = (f.placeholder ? `<option value="">${esc(f.placeholder)}</option>` : '') +
                    opts.map(o => `<option value="${esc(o.value)}" ${String(current) === String(o.value) ? 'selected' : ''}>${esc(o.label)}</option>`).join('');
            }).catch(() => {
                sel.innerHTML = `<option value="">Could not load options</option>`;
            });
        }
    });

    $('#saveBtn').addEventListener('click', async () => {
        const form = $('#entityForm', modalRoot);
        const banner = $('#formErrBanner', modalRoot);
        banner.style.display = 'none';
        let valid = true;
        cfg.fields.forEach(f => {
            const input = $(`[name="${f.name}"]`, form);
            const errBox = $(`[data-err="${f.name}"]`, form);
            if (!input) return;
            input.classList.remove('err');
            if (errBox) errBox.textContent = '';
            if (f.required && !input.value.trim()) {
                input.classList.add('err');
                if (errBox) errBox.textContent = 'Required';
                valid = false;
            }
        });
        if (!valid) return;

        const payload = {};
        cfg.fields.forEach(f => {
            const input = $(`[name="${f.name}"]`, form);
            if (!input) return;
            let val = input.value;
            if (f.type === 'number' || f.type === 'select-number') val = val === '' ? null : Number(val);
            if (f.type === 'datetime') val = val ? new Date(val).toISOString().slice(0,19) : null;
            payload[f.name] = val === '' ? null : val;
        });
        if (cfg.transformPayload) Object.assign(payload, cfg.transformPayload(payload, existing));

        const btn = $('#saveBtn');
        btn.disabled = true;
        btn.textContent = isEdit ? 'Saving…' : 'Creating…';
        try {
            if (isEdit) {
                await Api.put(`${cfg.path}/${existing[idField]}`, payload);
                toast('Saved', `${cfg.singular || cfg.title} updated.`);
            } else {
                await Api.post(cfg.path, payload);
                toast('Created', `${cfg.singular || cfg.title} added.`);
            }
            closeModal();
            if (Views[cfg.key] && Views[cfg.key].reload) await Views[cfg.key].reload();
            refreshSidebarCounts();
            if (cfg.afterSave) cfg.afterSave();
        } catch (err) {
            banner.style.display = 'flex';
            banner.innerHTML = `${ICONS.alert}<div>${esc(err.message)}</div>`;
            btn.disabled = false;
            btn.textContent = isEdit ? 'Save changes' : 'Create';
        }
    });
}

function fieldHtml(f, existing) {
    const val = existing ? (existing[f.name] ?? '') : (f.default ?? '');
    const full = f.full ? ' full' : '';
    let input;
    if (f.type === 'select' || f.type === 'select-number') {
        const opts = typeof f.options === 'function' ? [] : (f.options || []);
        input = `<select name="${f.name}" ${f.disabled ? 'disabled' : ''}>
      ${f.placeholder ? `<option value="">${esc(f.placeholder)}</option>` : ''}
      ${opts.map(o => `<option value="${esc(o.value)}" ${String(val) === String(o.value) ? 'selected' : ''}>${esc(o.label)}</option>`).join('')}
    </select>`;
    } else if (f.type === 'textarea') {
        input = `<textarea name="${f.name}" rows="3" placeholder="${esc(f.placeholder||'')}">${esc(val)}</textarea>`;
    } else if (f.type === 'datetime') {
        input = `<input type="datetime-local" name="${f.name}" value="${esc(toDatetimeLocal(val))}">`;
    } else {
        input = `<input type="${f.type || 'text'}" name="${f.name}" value="${esc(val)}" placeholder="${esc(f.placeholder||'')}" ${f.step ? `step="${f.step}"` : ''} ${f.disabled ? 'disabled' : ''}>`;
    }
    return `
    <div class="form-field${full}">
      <label>${esc(f.label)}${f.required ? ' <span class="required-mark">*</span>' : ''}</label>
      ${input}
      ${f.hint ? `<div class="form-hint">${esc(f.hint)}</div>` : ''}
      <div class="field-err" data-err="${f.name}"></div>
    </div>`;
}

/* helper: turn a list of entities into {value,label} options */
function asOptions(list, valueKey, labelFn) {
    return list.map(item => ({ value: item[valueKey], label: labelFn(item) }));
}

/* =========================================================================
   AIRPORTS
   ========================================================================= */
function registerAirportsView() {
    makeCrudView({
        key: 'airports',
        path: '/airports',
        title: 'Airports',
        singular: 'Airport',
        emptyText: 'No airports in the network yet.',
        describeRow: r => `${r.airportName} (${r.airportCode})`,
        columns: [
            { key: 'airportCode', label: 'Code', render: r => `<span class="mono" style="font-weight:700;color:var(--ink);">${esc(r.airportCode)}</span>` },
            { key: 'airportName', label: 'Name' },
            { key: 'city', label: 'City' },
            { key: 'state', label: 'State' },
            { key: 'country', label: 'Country' },
            { key: 'timezone', label: 'Timezone', render: r => `<span class="mono">${esc(r.timezone)}</span>` },
            { key: 'status', label: 'Status', render: r => `<span class="${pillClass(r.status)} pill-light">${esc(r.status || '—')}</span>` },
        ],
        searchFields: ['airportCode', 'airportName', 'city', 'country'],
        fields: [
            { name: 'airportCode', label: 'Airport Code', required: true, placeholder: 'e.g. BOM' },
            { name: 'airportName', label: 'Airport Name', required: true, placeholder: 'e.g. Chhatrapati Shivaji Maharaj Intl' },
            { name: 'city', label: 'City', required: true },
            { name: 'state', label: 'State', required: true },
            { name: 'country', label: 'Country', required: true },
            { name: 'timezone', label: 'Timezone', required: true, placeholder: 'e.g. Asia/Kolkata' },
        ],
    });
}

/* =========================================================================
   AIRCRAFT
   ========================================================================= */
function registerAircraftView() {
    makeCrudView({
        key: 'aircraft',
        path: '/aircrafts',
        title: 'Aircraft',
        singular: 'Aircraft',
        emptyText: 'No aircraft registered in the fleet yet.',
        describeRow: r => `${r.registrationNumber} — ${r.manufacturer} ${r.model}`,
        columns: [
            { key: 'registrationNumber', label: 'Registration', render: r => `<span class="mono" style="font-weight:700;color:var(--ink);">${esc(r.registrationNumber)}</span>` },
            { key: 'manufacturer', label: 'Manufacturer' },
            { key: 'model', label: 'Model' },
            { key: 'status', label: 'Status', render: r => `<span class="${pillClass(r.status)} pill-light">${esc(r.status || '—')}</span>` },
            { key: 'createdAt', label: 'Added', render: r => fmtDate(r.createdAt) },
        ],
        searchFields: ['registrationNumber', 'manufacturer', 'model'],
        fields: [
            { name: 'registrationNumber', label: 'Registration Number', required: true, placeholder: 'e.g. VT-ANB' },
            { name: 'manufacturer', label: 'Manufacturer', required: true, placeholder: 'e.g. Airbus' },
            { name: 'model', label: 'Model', required: true, placeholder: 'e.g. A320neo' },
        ],
    });
}

/* =========================================================================
   SEATS
   ========================================================================= */
function registerSeatsView() {
    let aircraftCache = [];
    async function getAircraft() {
        if (!aircraftCache.length) aircraftCache = await Api.get('/aircrafts');
        return aircraftCache;
    }
    makeCrudView({
        key: 'seats',
        path: '/seats',
        title: 'Seats',
        singular: 'Seat',
        emptyText: 'No seats defined yet. Create a seat map for an aircraft.',
        describeRow: r => `seat ${r.seatNumber}`,
        columns: [
            { key: 'seatNumber', label: 'Seat #', render: r => `<span class="mono" style="font-weight:700;">${esc(r.seatNumber)}</span>` },
            { key: 'seatClass', label: 'Class', render: r => `<span class="${pillClass(r.seatClass==='BUSINESS'?'boarding':'scheduled')} pill-light">${esc(r.seatClass)}</span>` },
            { key: 'seatType', label: 'Type' },
            { key: 'aircraftId', label: 'Aircraft', render: r => `<span class="mono">#${esc(r.aircraftId)}</span>` },
        ],
        searchFields: ['seatNumber', 'seatClass', 'seatType'],
        fields: [
            { name: 'seatNumber', label: 'Seat Number', required: true, placeholder: 'e.g. 14C' },
            { name: 'seatClass', label: 'Seat Class', type: 'select', required: true, options: [
                    { value: 'ECONOMY', label: 'Economy' }, { value: 'BUSINESS', label: 'Business' },
                ]},
            { name: 'seatType', label: 'Seat Type', type: 'select', required: true, options: [
                    { value: 'WINDOW', label: 'Window' }, { value: 'MIDDLE', label: 'Middle' }, { value: 'AISLE', label: 'Aisle' },
                ]},
            { name: 'aircraftId', label: 'Aircraft', type: 'select-number', required: true, placeholder: 'Select aircraft', full: true,
                options: async () => asOptions(await getAircraft(), 'id', a => `${a.registrationNumber} — ${a.manufacturer} ${a.model}`) },
        ],
    });
}

/* =========================================================================
   FLIGHTS
   ========================================================================= */
function registerFlightsView() {
    let airportCache = [], aircraftCache = [];
    async function getAirports() { if (!airportCache.length) airportCache = await Api.get('/airports'); return airportCache; }
    async function getAircraft() { if (!aircraftCache.length) aircraftCache = await Api.get('/aircrafts'); return aircraftCache; }

    const view = makeCrudView({
        key: 'flights',
        path: '/flights',
        title: 'Flights',
        singular: 'Flight',
        emptyText: 'No flights scheduled yet.',
        describeRow: r => `flight ${r.flightNumber}`,
        columns: [
            { key: 'flightNumber', label: 'Flight', render: r => `<span class="mono" style="font-weight:700;">${esc(r.flightNumber)}</span>` },
            { key: 'sourceAirportId', label: 'From', render: r => `<span class="mono">#${esc(r.sourceAirportId)}</span>` },
            { key: 'destinationAirportId', label: 'To', render: r => `<span class="mono">#${esc(r.destinationAirportId)}</span>` },
            { key: 'departureTime', label: 'Departs', render: r => fmtDateTime(r.departureTime) },
            { key: 'arrivalTime', label: 'Arrives', render: r => fmtDateTime(r.arrivalTime) },
            { key: 'basePrice', label: 'Fare', render: r => fmtMoney(r.basePrice) },
            { key: 'status', label: 'Status', render: r => `<span class="${pillClass(r.status)} pill-light">${esc(r.status||'—')}</span>` },
        ],
        searchFields: ['flightNumber', 'status'],
        fields: [
            { name: 'flightNumber', label: 'Flight Number', required: true, placeholder: 'e.g. AI-202' },
            { name: 'basePrice', label: 'Base Price (₹)', type: 'number', step: '0.01', required: true, placeholder: 'e.g. 4500' },
            { name: 'sourceAirportId', label: 'Source Airport', type: 'select-number', required: true, placeholder: 'Select airport',
                options: async () => asOptions(await getAirports(), 'id', a => `${a.airportCode} — ${a.city}`) },
            { name: 'destinationAirportId', label: 'Destination Airport', type: 'select-number', required: true, placeholder: 'Select airport',
                options: async () => asOptions(await getAirports(), 'id', a => `${a.airportCode} — ${a.city}`) },
            { name: 'aircraftId', label: 'Aircraft', type: 'select-number', required: true, placeholder: 'Select aircraft', full: true,
                options: async () => asOptions(await getAircraft(), 'id', a => `${a.registrationNumber} — ${a.manufacturer} ${a.model}`) },
            { name: 'departureTime', label: 'Departure Time', type: 'datetime', full: true },
            { name: 'arrivalTime', label: 'Arrival Time', type: 'datetime', full: true },
        ],
    });

    Views.flights.topbarActions = () => `
    <button class="btn btn-ghost" id="flightSearchBtn">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="7"/><path d="M21 21l-4.3-4.3"/></svg>
      Search routes
    </button>
  `;
    document.addEventListener('click', (e) => {
        if (e.target && e.target.closest && e.target.closest('#flightSearchBtn')) openFlightSearchModal();
    });
}

async function openFlightSearchModal() {
    openModal(`
    <div class="modal-head">
      <h3>Search Flights</h3>
      <button class="close-x" data-close>${ICONS_X}</button>
    </div>
    <div class="modal-body">
      <div class="help-banner error" id="searchErrBanner" style="display:none;"></div>
      <form id="searchForm">
        <div class="form-grid">
          <div class="form-field full">
            <label>Source City <span class="required-mark">*</span></label>
            <input type="text" name="sourcecity" placeholder="e.g. Mumbai">
            <div class="field-err"></div>
          </div>
          <div class="form-field full">
            <label>Destination City <span class="required-mark">*</span></label>
            <input type="text" name="destinationcity" placeholder="e.g. Delhi">
            <div class="field-err"></div>
          </div>
          <div class="form-field full">
            <label>Travel Date <span class="required-mark">*</span></label>
            <input type="date" name="travelDate">
            <div class="field-err"></div>
          </div>
        </div>
      </form>
      <div id="searchResults" style="margin-top:16px;"></div>
    </div>
    <div class="modal-foot">
      <button class="btn btn-ghost" data-close>Close</button>
      <button class="btn btn-primary" id="runSearchBtn">Search</button>
    </div>
  `, { wide: true });
    $all('[data-close]', modalRoot).forEach(b => b.addEventListener('click', closeModal));
    $('#runSearchBtn').addEventListener('click', async () => {
        const form = $('#searchForm', modalRoot);
        const src = form.sourcecity.value.trim();
        const dst = form.destinationcity.value.trim();
        const date = form.travelDate.value;
        const banner = $('#searchErrBanner');
        banner.style.display = 'none';
        if (!src || !dst || !date) {
            banner.style.display = 'flex';
            banner.innerHTML = `${ICONS.alert}<div>All three fields are required.</div>`;
            return;
        }
        const resultsBox = $('#searchResults');
        resultsBox.innerHTML = `<div class="loader-inline"><span class="spin"></span> Searching…</div>`;
        try {
            const qs = new URLSearchParams({ sourcecity: src, destinationcity: dst, travelDate: date });
            const results = await Api.get(`/flights/search?${qs.toString()}`);
            if (!results.length) {
                resultsBox.innerHTML = `<div class="empty-state"><p>No flights found for that route and date.</p></div>`;
                return;
            }
            resultsBox.innerHTML = `
        <table class="data"><thead><tr><th>Flight</th><th>Departs</th><th>Arrives</th><th>Fare</th><th>Status</th></tr></thead>
        <tbody>${results.map(f => `
          <tr>
            <td class="mono" style="font-weight:700;">${esc(f.flightNumber)}</td>
            <td>${fmtDateTime(f.departureTime)}</td>
            <td>${fmtDateTime(f.arrivalTime)}</td>
            <td>${fmtMoney(f.basePrice)}</td>
            <td><span class="${pillClass(f.status)} pill-light">${esc(f.status||'—')}</span></td>
          </tr>`).join('')}</tbody></table>`;
        } catch (err) {
            banner.style.display = 'flex';
            banner.innerHTML = `${ICONS.alert}<div>${esc(err.message)}</div>`;
            resultsBox.innerHTML = '';
        }
    });
}

/* =========================================================================
   FLIGHT SEATS
   ========================================================================= */
function registerFlightSeatsView() {
    let flightCache = [], seatCache = [];
    async function getFlights() { if (!flightCache.length) flightCache = await Api.get('/flights'); return flightCache; }
    async function getSeats() { if (!seatCache.length) seatCache = await Api.get('/seats'); return seatCache; }

    makeCrudView({
        key: 'flightseats',
        path: '/flightseats',
        title: 'Flight Seats',
        singular: 'Flight Seat',
        emptyText: 'No flight seat inventory yet. Assign seats to a flight.',
        describeRow: r => `flight seat #${r.id}`,
        columns: [
            { key: 'id', label: 'ID', render: r => `<span class="mono">#${esc(r.id)}</span>` },
            { key: 'flightId', label: 'Flight', render: r => `<span class="mono">#${esc(r.flightId)}</span>` },
            { key: 'seatId', label: 'Seat', render: r => `<span class="mono">#${esc(r.seatId)}</span>` },
            { key: 'status', label: 'Status', render: r => `<span class="${pillClass(r.status)} pill-light">${esc(r.status||'—')}</span>` },
        ],
        searchFields: ['flightId', 'seatId', 'status'],
        fields: [
            { name: 'flightId', label: 'Flight', type: 'select-number', required: true, placeholder: 'Select flight', full: true,
                options: async () => asOptions(await getFlights(), 'id', f => `${f.flightNumber} — ${fmtDateTime(f.departureTime)}`) },
            { name: 'seatId', label: 'Seat', type: 'select-number', required: true, placeholder: 'Select seat', full: true,
                options: async () => asOptions(await getSeats(), 'id', s => `${s.seatNumber} (${s.seatClass}, aircraft #${s.aircraftId})`) },
            { name: 'status', label: 'Status', type: 'select', placeholder: 'Default: AVAILABLE', options: [
                    { value: 'AVAILABLE', label: 'Available' }, { value: 'HELD', label: 'Held' },
                    { value: 'BOOKED', label: 'Booked' }, { value: 'BLOCKED', label: 'Blocked' },
                ]},
        ],
        extraRowActions: (row) => `<button class="icon-btn go" data-view-avail="${row.flightId}" title="View available seats for this flight">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 18l6-6-6-6"/></svg>
    </button>`,
    });

    document.addEventListener('click', (e) => {
        const btn = e.target.closest && e.target.closest('[data-view-avail]');
        if (btn) showAvailableSeats(btn.dataset.viewAvail);
    });
}

async function showAvailableSeats(flightId) {
    openModal(`
    <div class="modal-head">
      <h3>Available Seats · Flight #${esc(flightId)}</h3>
      <button class="close-x" data-close>${ICONS_X}</button>
    </div>
    <div class="modal-body" id="availBody"><div class="loader-inline"><span class="spin"></span> Loading…</div></div>
    <div class="modal-foot"><button class="btn btn-ghost" data-close>Close</button></div>
  `, { wide: true });
    $all('[data-close]', modalRoot).forEach(b => b.addEventListener('click', closeModal));
    try {
        const seats = await Api.get(`/flightseats/available/${flightId}`);
        const body = $('#availBody');
        if (!seats.length) {
            body.innerHTML = `<div class="empty-state"><p>No available seats for this flight right now.</p></div>`;
            return;
        }
        body.innerHTML = `<table class="data"><thead><tr><th>Seat #</th><th>Class</th><th>Type</th></tr></thead>
      <tbody>${seats.map(s => `<tr><td class="mono" style="font-weight:700;">${esc(s.seatNumber)}</td><td><span class="${pillClass(s.seatClass==='BUSINESS'?'boarding':'scheduled')} pill-light">${esc(s.seatClass)}</span></td><td>${esc(s.seatType)}</td></tr>`).join('')}</tbody></table>`;
    } catch (err) {
        $('#availBody').innerHTML = `<div class="help-banner error">${ICONS.alert}<div>${esc(err.message)}</div></div>`;
    }
}

/* =========================================================================
   BOOKINGS  (+ complete / cancel workflows)
   ========================================================================= */
function registerBookingsView() {
    let userCache = [], flightCache = [], flightSeatCache = [];
    async function getUsers() { if (!userCache.length) userCache = await Api.get('/users'); return userCache; }
    async function getFlights() { if (!flightCache.length) flightCache = await Api.get('/flights'); return flightCache; }
    async function getFlightSeats() { flightSeatCache = await Api.get('/flightseats'); return flightSeatCache; }

    makeCrudView({
        key: 'bookings',
        path: '/bookings',
        title: 'Bookings',
        singular: 'Booking',
        emptyText: 'No bookings yet.',
        describeRow: r => `booking ${r.pnr || '#' + r.id}`,
        columns: [
            { key: 'pnr', label: 'PNR', render: r => `<span class="mono" style="font-weight:700;color:var(--ink);">${esc(r.pnr || '—')}</span>` },
            { key: 'userId', label: 'User', render: r => `<span class="mono">#${esc(r.userId)}</span>` },
            { key: 'flightId', label: 'Flight', render: r => `<span class="mono">#${esc(r.flightId)}</span>` },
            { key: 'bookingTime', label: 'Booked At', render: r => fmtDateTime(r.bookingTime) },
            { key: 'status', label: 'Status', render: r => `<span class="${pillClass(r.status)} pill-light">${esc(r.status||'—')}</span>` },
        ],
        searchFields: ['pnr', 'status'],
        fields: [
            { name: 'userId', label: 'User', type: 'select-number', required: true, placeholder: 'Select user',
                options: async () => asOptions(await getUsers(), 'id', u => `${u.firstName} ${u.lastName} (${u.email})`) },
            { name: 'flightId', label: 'Flight', type: 'select-number', required: true, placeholder: 'Select flight',
                options: async () => asOptions(await getFlights(), 'id', f => `${f.flightNumber} — ${fmtDateTime(f.departureTime)}`) },
        ],
        extraRowActions: (row) => `
      ${row.status === 'PENDING' ? `<button class="icon-btn go" data-complete="${row.id}" title="Complete booking">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M8 12l3 3 5-6"/></svg>
      </button>` : ''}
      ${(row.status === 'PENDING' || row.status === 'CONFIRMED') ? `<button class="icon-btn danger" data-cancel="${row.id}" title="Cancel booking">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M15 9l-6 6M9 9l6 6"/></svg>
      </button>` : ''}
    `,
    });

    document.addEventListener('click', (e) => {
        const c = e.target.closest && e.target.closest('[data-complete]');
        if (c) openCompleteBookingModal(c.dataset.complete, getUsers, getFlights, getFlightSeats);
        const x = e.target.closest && e.target.closest('[data-cancel]');
        if (x) openCancelBookingModal(x.dataset.cancel);
    });
}

async function openCompleteBookingModal(bookingId, getUsers, getFlights, getFlightSeats) {
    openModal(`
    <div class="modal-head">
      <h3>Complete Booking #${esc(bookingId)}</h3>
      <button class="close-x" data-close>${ICONS_X}</button>
    </div>
    <div class="modal-body">
      <div class="help-banner" style="margin-bottom:16px;">${ICONS.alert}<div>This issues a ticket and records payment in one step — passenger and payment details are required.</div></div>
      <div class="help-banner error" id="cbErrBanner" style="display:none;"></div>
      <form id="completeForm">
        <div class="form-grid">
          <div class="form-field full">
            <label>User <span class="required-mark">*</span></label>
            <select name="userId" id="cbUser"><option value="">Loading…</option></select>
            <div class="field-err"></div>
          </div>
          <div class="form-field full">
            <label>Flight <span class="required-mark">*</span></label>
            <select name="flightId" id="cbFlight"><option value="">Loading…</option></select>
            <div class="field-err"></div>
          </div>
          <div class="form-field full">
            <label>Flight Seat <span class="required-mark">*</span></label>
            <select name="flightSeatId" id="cbSeat"><option value="">Select a flight first</option></select>
            <div class="field-err"></div>
            <div class="form-hint">Only available seats for the selected flight.</div>
          </div>
          <div class="form-field"><label>First Name <span class="required-mark">*</span></label><input name="firstName"><div class="field-err"></div></div>
          <div class="form-field"><label>Last Name <span class="required-mark">*</span></label><input name="lastName"><div class="field-err"></div></div>
          <div class="form-field"><label>Age <span class="required-mark">*</span></label><input name="age" type="number" min="1"><div class="field-err"></div></div>
          <div class="form-field">
            <label>Gender <span class="required-mark">*</span></label>
            <select name="gender"><option value="">Select</option><option value="MALE">Male</option><option value="FEMALE">Female</option><option value="OTHER">Other</option></select>
            <div class="field-err"></div>
          </div>
          <div class="form-field full"><label>Passport Number <span class="required-mark">*</span></label><input name="passportNumber"><div class="field-err"></div></div>
          <div class="form-field"><label>Amount (₹) <span class="required-mark">*</span></label><input name="amount" type="number" step="0.01" min="0.01"><div class="field-err"></div></div>
          <div class="form-field">
            <label>Payment Method <span class="required-mark">*</span></label>
            <select name="paymentMethod"><option value="">Select</option><option value="UPI">UPI</option><option value="CARD">Card</option><option value="NET_BANKING">Net Banking</option></select>
            <div class="field-err"></div>
          </div>
        </div>
      </form>
    </div>
    <div class="modal-foot">
      <button class="btn btn-ghost" data-close>Cancel</button>
      <button class="btn btn-amber" id="cbSubmit">Complete Booking</button>
    </div>
  `, { wide: true });
    $all('[data-close]', modalRoot).forEach(b => b.addEventListener('click', closeModal));

    const userSel = $('#cbUser'), flightSel = $('#cbFlight'), seatSel = $('#cbSeat');
    getUsers().then(users => {
        userSel.innerHTML = `<option value="">Select user</option>` + users.map(u => `<option value="${u.id}">${esc(u.firstName)} ${esc(u.lastName)} (${esc(u.email)})</option>`).join('');
    });
    getFlights().then(flights => {
        flightSel.innerHTML = `<option value="">Select flight</option>` + flights.map(f => `<option value="${f.id}">${esc(f.flightNumber)} — ${fmtDateTime(f.departureTime)}</option>`).join('');
    });
    flightSel.addEventListener('change', async () => {
        if (!flightSel.value) { seatSel.innerHTML = `<option value="">Select a flight first</option>`; return; }
        seatSel.innerHTML = `<option value="">Loading seats…</option>`;
        try {
            const seats = await Api.get(`/flightseats/available/${flightSel.value}`);
            if (!seats.length) { seatSel.innerHTML = `<option value="">No available seats</option>`; return; }
            seatSel.innerHTML = `<option value="">Select seat</option>` + seats.map(s => `<option value="${s.flightSeatId}">${esc(s.seatNumber)} · ${esc(s.seatClass)} · ${esc(s.seatType)}</option>`).join('');
        } catch {
            seatSel.innerHTML = `<option value="">Could not load seats</option>`;
        }
    });

    $('#cbSubmit').addEventListener('click', async () => {
        const form = $('#completeForm');
        const banner = $('#cbErrBanner');
        banner.style.display = 'none';
        const required = ['userId','flightId','flightSeatId','firstName','lastName','age','gender','passportNumber','amount','paymentMethod'];
        let valid = true;
        required.forEach(name => {
            const input = form[name];
            const errBox = input.closest('.form-field').querySelector('.field-err');
            input.classList.remove('err'); errBox.textContent = '';
            if (!String(input.value).trim()) { input.classList.add('err'); errBox.textContent = 'Required'; valid = false; }
        });
        if (!valid) return;
        const payload = {
            userId: Number(form.userId.value),
            flightId: Number(form.flightId.value),
            flightSeatId: Number(form.flightSeatId.value),
            firstName: form.firstName.value.trim(),
            lastName: form.lastName.value.trim(),
            age: Number(form.age.value),
            gender: form.gender.value,
            passportNumber: form.passportNumber.value.trim(),
            amount: Number(form.amount.value),
            paymentMethod: form.paymentMethod.value,
        };
        const btn = $('#cbSubmit');
        btn.disabled = true; btn.textContent = 'Processing…';
        try {
            const result = await Api.post('/bookings/complete', payload);
            closeModal();
            toast('Booking completed', `PNR ${result.pnr} · Ticket ${result.ticketNumber}`);
            if (Views.bookings.reload) await Views.bookings.reload();
            refreshSidebarCounts();
        } catch (err) {
            banner.style.display = 'flex';
            banner.innerHTML = `${ICONS.alert}<div>${esc(err.message)}</div>`;
            btn.disabled = false; btn.textContent = 'Complete Booking';
        }
    });
}

function openCancelBookingModal(bookingId) {
    confirmDialog({
        title: 'Cancel Booking',
        message: `Cancel booking #${bookingId}? Any associated ticket and payment will be reversed.`,
        sub: 'This action cannot be undone.',
        confirmLabel: 'Cancel Booking',
        danger: true,
        onConfirm: async () => {
            const result = await Api.post('/bookings/cancel', { bookingId: Number(bookingId) });
            toast('Booking cancelled', result.message || `PNR ${result.pnr} cancelled.`);
            if (Views.bookings.reload) await Views.bookings.reload();
            refreshSidebarCounts();
        }
    });
}

/* =========================================================================
   PASSENGERS
   ========================================================================= */
function registerPassengersView() {
    let bookingCache = [];
    async function getBookings() { if (!bookingCache.length) bookingCache = await Api.get('/bookings'); return bookingCache; }

    makeCrudView({
        key: 'passengers',
        path: '/passengers',
        title: 'Passengers',
        singular: 'Passenger',
        emptyText: 'No passengers added yet.',
        describeRow: r => `${r.firstName} ${r.lastName}`,
        columns: [
            { key: 'firstName', label: 'Name', render: r => `${esc(r.firstName)} ${esc(r.lastName)}` },
            { key: 'age', label: 'Age' },
            { key: 'gender', label: 'Gender' },
            { key: 'passportNumber', label: 'Passport', render: r => `<span class="mono">${esc(r.passportNumber)}</span>` },
            { key: 'bookingId', label: 'Booking', render: r => `<span class="mono">#${esc(r.bookingId)}</span>` },
        ],
        searchFields: ['firstName', 'lastName', 'passportNumber'],
        fields: [
            { name: 'firstName', label: 'First Name', required: true },
            { name: 'lastName', label: 'Last Name', required: true },
            { name: 'age', label: 'Age', type: 'number', required: true },
            { name: 'gender', label: 'Gender', type: 'select', required: true, options: [
                    { value: 'MALE', label: 'Male' }, { value: 'FEMALE', label: 'Female' }, { value: 'OTHER', label: 'Other' },
                ]},
            { name: 'passportNumber', label: 'Passport Number', required: true, full: true },
            { name: 'bookingId', label: 'Booking', type: 'select-number', required: true, placeholder: 'Select booking', full: true,
                options: async () => asOptions(await getBookings(), 'id', b => `${b.pnr || '#'+b.id} — ${b.status}`) },
        ],
    });
}

/* =========================================================================
   TICKETS
   ========================================================================= */
function registerTicketsView() {
    let bookingCache = [], passengerCache = [], flightSeatCache = [];
    async function getBookings() { if (!bookingCache.length) bookingCache = await Api.get('/bookings'); return bookingCache; }
    async function getPassengers() { if (!passengerCache.length) passengerCache = await Api.get('/passengers'); return passengerCache; }
    async function getFlightSeats() { if (!flightSeatCache.length) flightSeatCache = await Api.get('/flightseats'); return flightSeatCache; }

    makeCrudView({
        key: 'tickets',
        path: '/tickets',
        title: 'Tickets',
        singular: 'Ticket',
        emptyText: 'No tickets issued yet.',
        describeRow: r => `ticket ${r.ticketNumber || '#' + r.id}`,
        columns: [
            { key: 'ticketNumber', label: 'Ticket #', render: r => `<span class="mono" style="font-weight:700;">${esc(r.ticketNumber || '—')}</span>` },
            { key: 'bookingId', label: 'Booking', render: r => `<span class="mono">#${esc(r.bookingId)}</span>` },
            { key: 'passengerId', label: 'Passenger', render: r => `<span class="mono">#${esc(r.passengerId)}</span>` },
            { key: 'flightSeatId', label: 'Flight Seat', render: r => `<span class="mono">#${esc(r.flightSeatId)}</span>` },
            { key: 'status', label: 'Status', render: r => `<span class="${pillClass(r.status)} pill-light">${esc(r.status||'—')}</span>` },
        ],
        searchFields: ['ticketNumber', 'status'],
        fields: [
            { name: 'bookingId', label: 'Booking', type: 'select-number', required: true, placeholder: 'Select booking',
                options: async () => asOptions(await getBookings(), 'id', b => `${b.pnr || '#'+b.id} — ${b.status}`) },
            { name: 'passengerId', label: 'Passenger', type: 'select-number', required: true, placeholder: 'Select passenger',
                options: async () => asOptions(await getPassengers(), 'id', p => `${p.firstName} ${p.lastName} (${p.passportNumber})`) },
            { name: 'flightSeatId', label: 'Flight Seat', type: 'select-number', required: true, placeholder: 'Select flight seat', full: true,
                options: async () => asOptions(await getFlightSeats(), 'id', fs => `Flight #${fs.flightId} — Seat #${fs.seatId} (${fs.status})`) },
        ],
    });
}

/* =========================================================================
   PAYMENTS  (+ success workflow)
   ========================================================================= */
function registerPaymentsView() {
    let bookingCache = [];
    async function getBookings() { if (!bookingCache.length) bookingCache = await Api.get('/bookings'); return bookingCache; }

    makeCrudView({
        key: 'payments',
        path: '/payments',
        title: 'Payments',
        singular: 'Payment',
        emptyText: 'No payments recorded yet.',
        describeRow: r => `payment ${r.transactionId || '#' + r.id}`,
        columns: [
            { key: 'transactionId', label: 'Transaction', render: r => `<span class="mono" style="font-weight:700;">${esc(r.transactionId || '—')}</span>` },
            { key: 'bookingId', label: 'Booking', render: r => `<span class="mono">#${esc(r.bookingId)}</span>` },
            { key: 'amount', label: 'Amount', render: r => fmtMoney(r.amount) },
            { key: 'paymentMethod', label: 'Method' },
            { key: 'paymentStatus', label: 'Status', render: r => `<span class="${pillClass(r.paymentStatus)} pill-light">${esc(r.paymentStatus||'—')}</span>` },
            { key: 'paymentTime', label: 'Time', render: r => fmtDateTime(r.paymentTime) },
        ],
        searchFields: ['transactionId', 'paymentStatus', 'paymentMethod'],
        fields: [
            { name: 'bookingId', label: 'Booking', type: 'select-number', required: true, placeholder: 'Select booking', full: true,
                options: async () => asOptions(await getBookings(), 'id', b => `${b.pnr || '#'+b.id} — ${b.status}`) },
            { name: 'amount', label: 'Amount (₹)', type: 'number', step: '0.01', required: true },
            { name: 'paymentMethod', label: 'Payment Method', type: 'select', required: true, options: [
                    { value: 'UPI', label: 'UPI' }, { value: 'CARD', label: 'Card' }, { value: 'NET_BANKING', label: 'Net Banking' },
                ]},
        ],
        extraRowActions: (row) => row.paymentStatus === 'PENDING' ? `<button class="icon-btn go" data-pay-success="${row.id}" title="Mark payment successful">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M8 12l3 3 5-6"/></svg>
    </button>` : '',
    });

    document.addEventListener('click', (e) => {
        const btn = e.target.closest && e.target.closest('[data-pay-success]');
        if (btn) {
            confirmDialog({
                title: 'Confirm Payment Success',
                message: `Mark payment #${btn.dataset.paySuccess} as successful? This will confirm the related booking.`,
                confirmLabel: 'Confirm Success',
                danger: false,
                onConfirm: async () => {
                    const result = await Api.post('/payments/complete', { paymentId: Number(btn.dataset.paySuccess) });
                    toast('Payment confirmed', result.message || `PNR ${result.pnr} confirmed.`);
                    if (Views.payments.reload) await Views.payments.reload();
                    if (Views.bookings && Views.bookings.reload) await Views.bookings.reload();
                    refreshSidebarCounts();
                }
            });
        }
    });
}

/* =========================================================================
   USERS
   ========================================================================= */
function registerUsersView() {
    makeCrudView({
        key: 'users',
        path: '/users',
        title: 'Users',
        singular: 'User',
        emptyText: 'No users registered yet.',
        describeRow: r => `${r.firstName} ${r.lastName}`,
        columns: [
            { key: 'firstName', label: 'Name', render: r => `${esc(r.firstName)} ${esc(r.lastName)}` },
            { key: 'email', label: 'Email' },
            { key: 'phoneNumber', label: 'Phone', render: r => `<span class="mono">${esc(r.phoneNumber)}</span>` },
            { key: 'role', label: 'Role', render: r => `<span class="${pillClass(r.role==='ADMIN'?'boarding':'scheduled')} pill-light">${esc(r.role||'—')}</span>` },
            { key: 'status', label: 'Status', render: r => `<span class="${pillClass(r.status)} pill-light">${esc(r.status||'—')}</span>` },
        ],
        searchFields: ['firstName', 'lastName', 'email'],
        fields: [
            { name: 'firstName', label: 'First Name', required: true },
            { name: 'lastName', label: 'Last Name', required: true },
            { name: 'email', label: 'Email', type: 'email', required: true, full: true },
            { name: 'phoneNumber', label: 'Phone Number', required: true },
            { name: 'password', label: 'Password', type: 'password', required: true, hint: 'Required by the API on both create and update — re-enter it here when editing.' },
        ],
    });
}

/* =========================================================================
   NOTIFICATIONS
   ========================================================================= */
function registerNotificationsView() {
    let userCache = [];
    async function getUsers() { if (!userCache.length) userCache = await Api.get('/users'); return userCache; }

    makeCrudView({
        key: 'notifications',
        path: '/notifications',
        title: 'Notifications',
        singular: 'Notification',
        emptyText: 'No notifications sent yet.',
        describeRow: r => r.title,
        columns: [
            { key: 'title', label: 'Title', render: r => `<strong>${esc(r.title)}</strong>` },
            { key: 'message', label: 'Message', render: r => `<span style="color:var(--ink-soft);">${esc((r.message||'').slice(0,60))}${(r.message||'').length>60?'…':''}</span>` },
            { key: 'userId', label: 'User', render: r => `<span class="mono">#${esc(r.userId)}</span>` },
            { key: 'notificationType', label: 'Type', render: r => `<span class="pill pill-light pill-booked">${esc(r.notificationType)}</span>` },
            { key: 'isRead', label: 'Read', render: r => r.isRead ? `<span class="pill pill-light pill-active">Read</span>` : `<span class="pill pill-light pill-pending">Unread</span>` },
            { key: 'createdAt', label: 'Sent', render: r => fmtDateTime(r.createdAt) },
        ],
        searchFields: ['title', 'message', 'notificationType'],
        fields: [
            { name: 'userId', label: 'User', type: 'select-number', required: true, placeholder: 'Select user', full: true,
                options: async () => asOptions(await getUsers(), 'id', u => `${u.firstName} ${u.lastName} (${u.email})`) },
            { name: 'title', label: 'Title', required: true, full: true },
            { name: 'message', label: 'Message', type: 'textarea', required: true, full: true },
            { name: 'notificationType', label: 'Type', type: 'select', required: true, full: true, options: [
                    { value: 'BOOKING', label: 'Booking' }, { value: 'PAYMENT', label: 'Payment' }, { value: 'TICKET', label: 'Ticket' },
                    { value: 'FLIGHT', label: 'Flight' }, { value: 'REFUND', label: 'Refund' }, { value: 'SYSTEM', label: 'System' },
                    { value: 'BOOKING_CONFIRMATION', label: 'Booking Confirmation' }, { value: 'BOOKING_CANCELLATION', label: 'Booking Cancellation' },
                ]},
        ],
    });
}

/* -------------------------------------------------------------------------
   Boot
   ------------------------------------------------------------------------- */
document.addEventListener('DOMContentLoaded', () => {
    buildAllViews();
    checkApiStatus();
    refreshSidebarCounts();
    Router.go('dashboard');
});