import ApiService from '../apiservice';
import ErrorValidation from '../Exception/ErrorValidation'

export default class LancamentoService extends ApiService {

    constructor() {
        super('/launches')
    }

    getListMonth(){
        return  [
            { label: 'SELECIONE...', value: null },
            { label: 'Janeiro', value: 1 },
            { label: 'Fevereiro', value: 2 },
            { label: 'Março', value: 3 },
            { label: 'Abril', value: 4 },
            { label: 'Maio', value: 5 },
            { label: 'Junho', value: 6 },
            { label: 'Julho', value: 7 },
            { label: 'Agosto', value: 8 },
            { label: 'Setembro', value: 9 },
            { label: 'Outubro', value: 10 },
            { label: 'Novembro ', value: 11 },
            { label: 'Dezembro ', value: 12 }
        ]
    }

    getListType(){
        return [
            { label: 'SELECIONE...', value: null },
            { label: 'Despesa', value: "EXPENSE" },
            { label: 'Receita', value: "RECIPE" }
        ]
    }

    insert(launch){
        return this.post('/', launch);
    }

    findById(id){
        return this.get(`/${id}`);
    }

    updateById(launch){
        return this.put(`/${launch.id}`, launch);
    }

    findAll(launchFilter) {
        let params = `?year=${launchFilter.year}`


        if (launchFilter.month) {
            params = `${params}&month=${launchFilter.month}`
        }

        if (launchFilter.type) {
            params = `${params}&type=${launchFilter.type}`
        }

        if (launchFilter.status) {
            params = `${params}&status=${launchFilter.status}`
        }

        if (launchFilter.user) {
            params = `${params}&user=${launchFilter.user}`
        }

        if (launchFilter.description) {
            params = `${params}&description=${launchFilter.description}`
        }

        return this.get(params)
    }

    deleter(id){
        return this.delete(`/${id}`)
    }

    updateStatus(id, status){
        return this.put(`/${id}/updateStatus`, { status } )
    }

    validate(launch){
        const errors = [];

        if (!launch.description) {
            errors.push('O campo Descrição é Obrigatório.')
        }

        if (!launch.month) {
            errors.push('O campo Mês é Obrigatório.')
        }
        if (!launch.year) {
            errors.push('O campo Ano é Obrigatório.')
        }
        if (!launch.value) {
            errors.push('O campo Valor é Obrigatório.')
        }
        if (!launch.type) {
            errors.push('O campo Tipo é Obrigatório.')
        }

        if (errors && errors.length > 0) {
            throw new ErrorValidation(errors);
        }
    }
}