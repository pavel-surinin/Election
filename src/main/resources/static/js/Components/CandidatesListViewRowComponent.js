var CandidatesListViewRowComponent = React.createClass({

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
                {this.props.partijosPavadinimas}
              </td>
              <td>

              <button type="button" className="btn btn-warning btn-sm" data-toggle="modal" data-target="#myModal">Detali informacija</button>
              <div id="myModal" className="modal fade" role="dialog">
                <div className="modal-dialog">
                  <div className="modal-content">
                    <div className="modal-header">
                      <button type="button" className="close" data-dismiss="modal">&times;</button>
                      <h4 className="modal-title">{this.props.name} {this.props.surname}</h4>
                    </div>
                    <div className="modal-body">
                      {this.props.description}
                    </div>
                    <div className="modal-footer">
                      <button type="button" className="btn btn-default" data-dismiss="modal">Uždaryti</button>
                    </div>
                  </div>
                </div>
              </div>

              </td>
              <td>
                <div>
                	<button type="button" onClick={this.edit} className="btn btn-primary" aria-label="Left Align">
                      Redaguoti
                    </button>
                    <button type="button" onClick={this.delete} className="btn btn-danger" aria-label="Left Align">
                      Šalinti
                    </button>
               </div>
              </td>
            </tr>
    );
  }
});

window.CandidatesListViewRowComponent = CandidatesListViewRowComponent;