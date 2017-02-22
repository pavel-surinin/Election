var ResultsSingleListRowComponent = React.createClass({
  approve : function(){
    this.props.onHandleApprove(this.props.info.id,'single',this.props.info.name);
  },
  delete : function(){
    this.props.onHandleDelete(this.props.info.id,'single',this.props.info.name);
  },
  render: function() {
    console.log("bla bla bla: ", this.props.info);
    return (
      <tr>
        <td className="small">
          {this.props.info.name}
        </td>
        <td className="small">
          {this.props.info.representativeName}
        </td>
        <td className="small">
          {this.props.info.votesSingleRegisteredDate}
        </td>
        <td>
          <div>
              &nbsp;
              <button onClick={this.approve} ref="confirm" data-toggle="tooltip1" title="Patvirtinti" type="button" className="btn btn-success btn-sm fa fa-check"></button>
              &nbsp;
            <button data-target={"#confirmationModal" + this.props.info.type + this.props.info.id} ref="delete" title="Ištrinti" type="button" id={'confirm-delete-button-' + this.props.info.type + this.props.info.id} className="btn btn-danger btn-sm fa fa-trash" data-toggle="modal"></button>
            <div id={'confirmationModal' + this.props.info.type + this.props.info.id} className="modal fade" role="dialog">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" id="modal-close-button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  </div>
                  <div className="modal-body">
                    <h5 className="modal-title">Ar tikrai norite ištrinti {this.props.info.name} vienmandatės apygardos rezultatus?</h5>
                  </div>
                  <div className="modal-footer">
                    <div className="btn-group">
                        <button
                          ref='delete'
                          onClick={this.delete}
                          data-toggle="tooltip1"
                          title="Ištrinti"
                          type="button"
                          className="btn btn-default"
                          data-dismiss="modal">Taip
                        </button>
                        <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Ne</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </td>
      </tr>
    );
  }

});

window.ResultsSingleListRowComponent = ResultsSingleListRowComponent;
