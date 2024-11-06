import {createRouter, createWebHistory} from "vue-router";
import MainRouter from "@/router/MainRouter.js";

import AdminRouter from "@/router/AdminRouter.js"

const routes = [
    ...MainRouter,

    ...AdminRouter
]

const router = createRouter({
    history: createWebHistory(),
    routes,
    // 라우팅 시 화면 최상단으로 이동됨.
    scrollBehavior(to, from, savedPosition) {
        // savedPosition이 있는 경우(예: 뒤로 가기), 해당 위치로 이동
        if (savedPosition) {
            return savedPosition;
        } else {
            // 새로운 페이지 이동 시 맨 위로 스크롤
            return { top: 0 };
        }
    }
});

export default router;