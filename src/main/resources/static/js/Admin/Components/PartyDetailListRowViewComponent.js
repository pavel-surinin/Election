var PartyDetailListRowViewComponent = React.createClass({
  componentDidMount: function () {
    $(this.refs.delete).tooltip();
    $(this.refs.edit).tooltip();
    $(this.refs.info).tooltip();
  },

  render: function() {
    return (
      <tr>
        <td className="small">
          {this.props.numberInParty}
        </td>
        <td className="small">
          {this.props.name}
        </td>
        <td className="small">
          {this.props.surname}
        </td>
        <td className="small">
          {this.props.birthDate}
        </td>
        <td>
          <button type="button" ref="info" title="Aprašymas" id={'description-button-' + this.props.id} className="btn btn-info btn-sm fa fa-info" data-toggle="modal" data-target={'#' + this.props.id}>

          </button>
            <div className="modal fade" id={this.props.id} tabIndex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog" role="document">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" id="modal-close-button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 className="modal-title" id={this.props.id} >{this.props.name} {this.props.surname}</h4>
                  </div>
                  <div className="modal-body">
                    {this.props.description}
                  </div>
                  <div className="modal-footer">
                    <button type="button" id="close-button" className="btn btn-default btn-sm" data-dismiss="modal">Uždaryti</button>
                  </div>
                </div>
              </div>
            </div>
        </td>
      </tr>
    );
  }
});

window.PartyDetailListRowViewComponent = PartyDetailListRowViewComponent;
