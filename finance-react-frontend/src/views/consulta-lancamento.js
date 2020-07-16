import React from 'react';
import { withRouter } from 'react-router-dom';
import Card from '../components/card';
import FormGroup from '../components/form-group';
import SelectMenu from '../components/selectMenu';
import LancamentoTable from './lancamentoTable';

import LancamentoService from '../app/service/lancamentoService'
import LocalStorageService from '../app/service/localStorageService'
import { messageSuccess, messageError } from '../components/toastr';

import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';

class ConsultaLancamento extends React.Component {

    state = {
        year: '',
        month: '',
        type: '',
        description: '',
        showConfirmDialog: false,
        launchDelete: {},
        launch: []
    }

    constructor() {
        super();
        this.service = new LancamentoService();
    }

    findAll = () => {

        const userLogin = LocalStorageService.getlocalStorage('_user')

        const launchFilter = {
            year: this.state.year,
            description: this.state.description,
            month: this.state.month,
            type: this.state.type,
            user: userLogin.id
        }

        this.service
            .findAll(launchFilter)
            .then(response => {
                this.setState({ launch: response.data })
            }).catch(error => {
                console.log(error)
            })
    }

    updateById = (id) => {
        this.props.history.push(`/cadastraLancamento/${id}`)
    }

    openDialog = (launch) => {
        this.setState({ showConfirmDialog: true, launchDelete: launch })
    }

    deleteById = () => {
        this.service.deleter(this.state.launchDelete.id).
            then(response => {
                const launchs = this.state.launch;
                const index = launchs.indexOf(this.launchDelete);
                launchs.splice(index, 1);
                this.setState({ launchs: launchs, showConfirmDialog: false });
                messageSuccess('Deletado com sucesso...')
            }).catch(error => {
                messageError('Error ao deletar lançamento.')
            })
    }

    cancelDelete = () => {
        this.setState({ showConfirmDialog: false, launchDelete: {} })
    }

    updateStatus = (launchUpdateStatus, status) => {
        this.service.updateStatus(launchUpdateStatus.id, status)
            .then(response => {
                const launch = this.state.launch;
                const index = launch.indexOf(launchUpdateStatus);
                if(index !== -1){
                    launchUpdateStatus['status'] = status;
                    launch[index] = launchUpdateStatus;
                    this.setState({launch});
                }
                messageSuccess('Status Atualizado com sucesso.')
            })
    }

    render() {

        const mes = this.service.getListMonth();
        const tipo = this.service.getListType();

        const confirmDialog = (
            <div>
                <Button label="Confirmar" icon="pi pi-check" onClick={this.deleteById} />
                <Button label="Cancelar" icon="pi pi-times" onClick={this.cancelDelete} className="p-button-secondary" />
            </div>
        )

        return (
            <Card title="Consulta lançamentos">
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <FormGroup htmlFor="inputAno" label="Ano">
                                <input type="text" className="form-control" id="ano"
                                    value={this.state.year} onChange={e => this.setState({ year: e.target.value })} placeholder="Digite o Ano" />
                            </FormGroup>
                        </div>
                    </div>
                    <div className="col-md-12">
                        <div className="bs-component">
                            <FormGroup htmlFor="inputAno" label="Descrição">
                                <input type="text" className="form-control" id="description"
                                    value={this.state.description} onChange={e => this.setState({ description: e.target.value })} placeholder="Digite a Descrição..." />
                            </FormGroup>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup htmlFor="inputMes" label="Mes">
                                <SelectMenu value={this.state.month} onChange={e => this.setState({ month: e.target.value })}
                                    className="form-control" lista={mes} />
                            </FormGroup>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup htmlFor="inputTipo" label="Tipo ">
                                <SelectMenu className="form-control"
                                    value={this.state.type} onChange={e => this.setState({ type: e.target.value })}
                                    lista={tipo} />
                            </FormGroup>
                        </div>
                    </div>
                    <div className="col-md-12">
                        <button onClick={this.findAll} type="button" className="btn btn-success">Buscar</button>
                        <button onClick={e => this.props.history.push('/cadastraLancamento')} type="button" className="btn btn-danger">Cadastrar</button>
                    </div>
                </div>
                <br />
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <LancamentoTable lancamentos={this.state.launch}
                                delete={this.openDialog}
                                update={this.updateById} 
                                updateStatus={this.updateStatus}/>
                        </div>
                    </div>
                </div>
                <div>
                    <Dialog header="Confirmar Deleção"
                        visible={this.state.showConfirmDialog}
                        style={{ width: '50vw' }}
                        footer={confirmDialog}
                        modal={true} onHide={() => this.setState({ showConfirmDialog: false })}>
                        Realmente deseja deletar este lançamento...
                    </Dialog>
                </div>
            </Card>
        )
    }
}

export default ConsultaLancamento;