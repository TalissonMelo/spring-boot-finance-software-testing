import LocalStorageService from './localStorageService';

export default class AuthService {

    static isUserAuthenticate(){
        const user = LocalStorageService.getlocalStorage('_user');
        return user && user.id;
    }

    static removeUserAuthenticate(){
        LocalStorageService.removeUserLocalStorage('_user');
    }
}