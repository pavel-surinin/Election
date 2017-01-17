var CandidatesListViewRowComponent = React.createClass({

  render: function() {
    return (
            <tr>
              <td>
                #
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
                {this.props.party}
              </td>
              <td>

              <button type="button" className="btn btn-info btn-lg" data-toggle="Detali informacija" data-target="#myModal">Open Modal</button>

              <div id="myModal" className="modal fade" role="dialog">
                <div className="modal-dialog">

                  <div className="modal-content">
                    <div className="modal-header">
                      <button type="button" className="close" data-dismiss="modal">&times;</button>
                      <h4 className="modal-title">Modal Header</h4>
                    </div>
                    <div className="modal-body">
                      <p>Some text in the modal.</p>
                    </div>
                    <div className="modal-footer">
                      <button type="button" className="btn btn-info" data-dismiss="modal">Close</button>
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