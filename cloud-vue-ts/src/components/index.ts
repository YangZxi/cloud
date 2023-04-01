// import DataTable from "./DataTable"
// import Pagination from "./Pagination"

const components = {
  install: function(Vue: any) {
    Vue.component("YIcon", () => import("./mie-ui/CIcon.vue"));
    Vue.component("YPopup", () => import("./mobile/YPopup.vue"));
  }
}

export default components

