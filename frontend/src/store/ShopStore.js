import { defineStore } from 'pinia';
import { fetchShopList } from '@/api/shop/ShopListReadApi';

export const useShopStore = defineStore('shopStore', {
    state: () => ({
        shopSeqs: [],
        shopData: JSON.parse(localStorage.getItem('shopData')) || {
            userSeq: '',
            shopSeq: '',
            shopName: '',
            shopAddress: '',
            shopIntroduction: ''
        }
    }),

    actions: {
        async loadShopData() {
            try {
                const shopList = await fetchShopList(); // API 호출하여 shopList 가져오기
                if (shopList && shopList.length > 0) {
                    this.shopSeqs = shopList.map(shop => shop.shopSeq);
                    localStorage.setItem('shopSeq', JSON.stringify(this.shopSeqs)); // 로컬 저장소에 값 저장
                }
            } catch (error) {
                console.log('스토어에서 매장 고유번호 데이터를 로드하는 중 오류 발생', error);
            }
        },
        async loadOnwerShopData(){
            try {
                const shopInfo = await fetchOwnerShopInfo();
                this.shopData = shopInfo;

                // 로컬 스토리지에 저장
                localStorage.setItem('shopData', JSON.stringify(this.shopData));
            } catch (error) {
                console.log('스토어에서 매장 데이터를 로드하는 중 오류 발생 :', error);
            }
        }


    }
});