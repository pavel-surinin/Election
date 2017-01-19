var PartyDetailListRowViewComponent = React.createClass({

  render: function() {
    return (
      <tr>
        <td>
          {this.props.id}
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
          <button type="button" className="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
            Aprašymas
          </button>
            <div className="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog" role="document">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 className="modal-title" id="myModalLabel">Kandidato aprašymas</h4>
                  </div>
                  <div className="modal-body">
                    Kandidatas {this.props.name}
                  </div>
                  <div className="modal-body">
                    Toks ir  toks. Šitoks ir anoks
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
