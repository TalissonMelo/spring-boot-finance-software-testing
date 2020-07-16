import React from 'react';
import currentFormatter from 'currency-formatter'

export default props => {

    const rows = props.lancamentos.map(launch => {
        return (
            <tr key={launch.id}>
                <td>{launch.description} </td>
                <td>{currentFormatter.format(launch.value, { locale: 'pt-BR' })}</td>
                <td> {launch.type}</td>
                <td> {launch.month}</td>
                <td> {launch.status}</td>
                <td>
                    <button type="button" className="btn btn-success" onClick={e => props.updateStatus(launch, 'EFFECTIVE')}><i className="pi pi-check"></i></button>
                    <button type="button" className="btn btn-warning" onClick={e => props.updateStatus(launch, 'CANCELED')}><i className="pi pi-times"></i></button>
                    <button type="button" className="btn btn-primary" onClick={e => props.update(launch.id)} ><i className="pi pi-pencil"></i></button>
                    <button type="button" className="btn btn-danger" onClick={e => props.delete(launch)}><i className="pi pi-trash"></i></button>
                </td>
            </tr>
        )
    })
    return (
        <table className="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Descrição</th>
                    <th scope="col">Valor</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Mes</th>
                    <th scope="col">Situação</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                {rows}
            </tbody>
        </table>
    )
}