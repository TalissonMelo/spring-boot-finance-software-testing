import ApiService from '../apiservice'

class UserService extends ApiService {

    constructor() {
        super('/users')
    }

    login(authenticate) {
        return this.post('/login', authenticate)
    }

    balanceForUser(id){
        return this.get(`/${id}/balance`);
    }

    insert(user){
        return this.post('/', user);
    }
}

export default UserService