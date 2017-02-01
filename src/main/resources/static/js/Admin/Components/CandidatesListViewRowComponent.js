var CandidatesListViewRowComponent = React.createClass({
  componentDidMount: function () {
    $(this.refs.delete).tooltip();
    $(this.refs.edit).tooltip();
    $(this.refs.info).tooltip();
  },
  render: function() {
    return (
            <tr>
              <td className='small'>
              {this.props.numberInParty}
              </td>
              <td className='small'>
                {this.props.name}
              </td>
              <td className='small'>
                {this.props.surname}
              </td>
              <td className='small'>
                {this.props.birthDate}
              </td>
              <td className='small'>
                {this.props.partijosPavadinimas}
              </td>
              <td>
                <button ref="info" title="Aprašymas" id={'description-button-' + this.props.id} type="button"  className="btn btn-info btn-sm fa fa-info" data-toggle="modal" data-target={'#' + this.props.id}></button>
                <div id={this.props.id} className="modal fade" role="dialog">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" id="modal-close-button" className="close" data-dismiss="modal">&times;</button>
                        <h4 className="modal-title">{this.props.name} {this.props.surname}</h4>
                      </div>
                      <div className="modal-body">
                        {this.props.description}
                      </div>
                      <div className="modal-footer">
                        <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Uždaryti</button>
                      </div>
                    </div>
                  </div>
                </div>
                &nbsp;
                <button ref="edit" data-toggle="tooltip2" id={'edit-button-' + this.props.id} title="Redaguoti" type="button" className="btn btn-primary btn-sm fa fa-pencil"></button>
                &nbsp;
                <button ref="delete" data-toggle="tooltip1" id={'delete-button-' + this.props.id} title="Ištrinti" type="button" className="btn btn-danger btn-sm fa fa-trash"></button>
              </td>
            </tr>
    );
  }
});

window.CandidatesListViewRowComponent = CandidatesListViewRowComponent;
