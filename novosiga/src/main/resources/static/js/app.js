(() => {
  const STORAGE_KEY = "novosiga.sidebarCollapsed";
  const sidebar = document.getElementById("appSidebar");
  const backdrop = document.getElementById("sidebarBackdrop");
  const mobileToggles = document.querySelectorAll("[data-sidebar-toggle]");
  const collapseBtn = document.querySelector("[data-sidebar-collapse]");

  if (!sidebar) {
    return;
  }

  const setMobileOpen = (open) => {
    sidebar.classList.toggle("is-open", open);
    if (backdrop) {
      backdrop.classList.toggle("is-open", open);
    }
    document.body.style.overflow = open && window.innerWidth < 992 ? "hidden" : "";
  };

  const setCollapsed = (collapsed) => {
    document.documentElement.classList.toggle("sidebar-collapsed", collapsed);
    try {
      localStorage.setItem(STORAGE_KEY, collapsed ? "1" : "0");
    } catch (e) {}

    if (!collapseBtn) {
      return;
    }

    collapseBtn.setAttribute("aria-expanded", String(!collapsed));
    collapseBtn.setAttribute("aria-label", collapsed ? "Expandir menu" : "Recolher menu");
    const icon = collapseBtn.querySelector("i");
    if (icon) {
      icon.className = collapsed ? "bi bi-chevron-right" : "bi bi-chevron-left";
    }
  };

  mobileToggles.forEach((button) => {
    button.addEventListener("click", () => {
      setMobileOpen(!sidebar.classList.contains("is-open"));
    });
  });

  backdrop?.addEventListener("click", () => setMobileOpen(false));

  collapseBtn?.addEventListener("click", () => {
    setCollapsed(!document.documentElement.classList.contains("sidebar-collapsed"));
  });

  window.addEventListener("resize", () => {
    if (window.innerWidth >= 992) {
      setMobileOpen(false);
    }
  });

  setCollapsed(document.documentElement.classList.contains("sidebar-collapsed"));
})();
