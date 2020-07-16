import React from 'react';

import Card from '../components/card';
import FormGroup from '../components/form-group';
import SelectMenu from '../components/selectMenu';
import LancamentoService from '../app/service/lancamentoService';
import * as message from '../components/toastr';

import LocalStorageService from '../app/service/localStorageService';
import { withRouter } from 'react-router-dom';

class CadastroLancamento extends React.Component {

    state = {
        id: null,
        description: '',
        month: '',
        year: '',
        value: '',
        type: '',
        status: '',
        user: '',
        updatePag: false
    }

    constructor() {
        super();
        this.service = new LancamentoService();
    }

    componentDidMount() {
        const params = this.props.match.params;
        if (params.id) {
            this.service.findById(params.id)
                .then(response => {
                    this.setState({ ...response.data, updatePag: true })
                })
                .catch(errors => {
                    message.messageError(errors.response.data)
                })
        }
    }

    submit = () => {

        const userLog = LocalStorageService.getlocalStorage('_user');

        const { description, month, year, value, type } = this.state;
        const launch = { description, month, year, value, type, user: userLog.id };

        try {
            this.service.validate(launch);
        } catch (error) {
            const msg = error.message;
            msg.forEach(msgs => message.messageError(msgs))
            return false;
        }

        this.service.insert(launch)
            .then(response => {
                this.props.history.push('/consultaLancamento')
                message.messageSuccess('Lancamento' + launch.description + ", cadastrado...");
            }).catch(error => {
                console.log(error.response.data)
                message.messageError(error.response.data)
            })
    }

    update = () => {

        const { id, description, month, year, value, type, status, user } = this.state;
        const launch = { id, description, month, year, value, type, status, user };

        this.service.updateById(launch)
            .then(response => {
                this.props.history.push('/consultaLancamento')
                message.messageSuccess('Lançamento Atualizado com sucesso...');
            }).catch(error => {
                console.log(error.response.data)
                message.messageError(error.response.data)
            })
    }

    handleChange = (event) => {
        const value = event.target.value;
        const name = event.target.name;

        this.setState({ [name]: value })
    }

    render() {

        const types = this.service.getListType();
        const months = this.service.getListMonth();

        return (
            <Card title={this.state.updatePag ? 'Atualização de Lançamentos' : 'Cadastro de Lançamentos'} >
                <div className="row">
                    <div className="col-md-12">
                        <FormGroup id="inputDescription" label="Descrição **">
                            <input type="text" className="form-control"
                                name="description" value={this.state.description}
                                onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-6">
                        <FormGroup id="inputMes" label="Mês **">
                            <SelectMenu id="/inputMes" lista={months} className="form-control"
                                name="month" value={this.state.month}
                                onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                    <div className="col-md-6">
                        <FormGroup id="inputAno" label="Ano **">
                            <input type="text" className="form-control"
                                name="year" value={this.state.year}
                                onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-4">
                        <FormGroup id="inputValor" label="Valor **">
                            <input type="text" className="form-control"
                                name="value" value={this.state.value}
                                onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                    <div className="col-md-4">
                        <FormGroup id="inputType" label="Tipo **">
                            <SelectMenu id="/inputType" lista={types} className="form-control"
                                name="type" value={this.state.type}
                                onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                    <div className="col-md-4">
                        <FormGroup id="inputStatus" label="Status">
                            <input type="text" className="form-control" disabled
                                name="status" value={this.state.status} />
                        </FormGroup>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-4">
                        {this.state.updatePag ?
                            (
                                <button type="button" onClick={this.update} className="btn btn-primary">Atualizar</button>
                            ) :
                            (
                                <button type="button" onClick={this.submit} className="btn btn-success">Salvar</button>
                            )
                        }
                        <button type="button" onClick={e => this.props.history.push('/consultaLancamento')} className="btn btn-danger">Cancelar</button>
                    </div>
                </div>
            </Card>

        )
    }
}

export default withRouter(CadastroLancamento);