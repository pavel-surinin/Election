var PartyDetailListRowViewComponent = React.createClass({

  render: function() {
    return (
      <tr>
        <td>
          {this.props.numberInParty}
        </td>
        <td>
          {this.props.name}
        </td>
        <td>
          {this.props.surname}
        </td>
        <td>
          {this.props.birthDate}
        </td>
        <td>
          <button type="button" className="btn btn-primary btn-sm" data-toggle="modal" data-target={'#' + this.props.id}>
            Aprašymas
          </button>
            <div className="modal fade" id={this.props.id} tabIndex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog" role="document">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 className="modal-title" id={this.props.id} >{this.props.name} {this.props.surname}</h4>
                  </div>
                  <div className="modal-body">
                    {this.props.description}
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-default btn-sm" data-dismiss="modal">Uždaryti</button>
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
