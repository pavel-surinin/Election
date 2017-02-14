var PartyCreateEditFormComponent = React.createClass({
  onFileChange : function(){
    this.props.onHandleFileChange(this.refs.file.files[0]);
  },

  render: function() {
    var nameErrorMesages = validation.showMsg(this.props.nameErrorMesages);
    var numberErrorMesages = validation.showMsg(this.props.numberErrorMesages);
    var fileErrorMesages = validation.showMsg(this.props.fileErrorMesages);
    var uploadStyle = styles.toggleTF(false);
    var deleteBtnStyle = styles.toggleTF(true);
    var memCount = 0;
    for (var i = 0; i < this.props.members.length; i++) {
      if (this.props.members[i].multiList == true) {
        memCount++;
      }
    }
    if (memCount == 0) {
      uploadStyle = styles.toggleTF(true);
      deleteBtnStyle = styles.toggleTF(false);
    }
    return (
            <div className="row">
              <div className="col-md-6 col-md-offset-3 col-xs-12">
                <div className="panel panel-primary">
                  <div className="panel-heading text-center">
                  <h4><b>{this.props.action} partiją</b></h4>
                  </div>
                  <div className="panel-body">
                    <form onSubmit={this.props.onHandleSubmit} role="form">
                      <div className="input-group col-xs-12 text-primary">
                        <label>Partijos pavadinimas</label>
                          <input
                            id="name-input"
                            type="text"
                            onChange={this.props.onHandleNameChange}
                            value={this.props.name}
                            className="form-control"
                            placeholder="Pavadinimas"
                            required
                          />
                      </div>
                      <br/>
                      {nameErrorMesages}
                      <div className="input-group col-xs-12 text-primary">
                        <label>Partijos numeris</label>
                          <input
                            id="number-input"
                            type="text"
                            onChange={this.props.onHandleNumberChange}
                            value={this.props.number}
                            className="form-control"
                            placeholder="Partijos Sąrašo Numeris"
                          />
                      </div><br/>
                      {numberErrorMesages}
                      <div style={deleteBtnStyle} className="input-group col-xs-12 text-primary">
                          <button id="delete-button" onClick={this.props.onHandleDeleteClick} className='btn btn-block btn-danger btn-outline '> Ištrinti sąrašą
                          </button>
                      </div>
                      <div style={uploadStyle} className="input-group col-xs-12 text-primary">
                          <label>Prisegti partijos narių sąrašą</label>
                                <input className="btn btn-block btn-primary btn-outline" onChange={this.onFileChange} ref="file" type="file" name="file" id="file-select"/>
                          <p className="help-block">Pasirinkite ir įkelkite partijos sąrašo failą CSV formatu </p>
                      </div><br/>
                        {fileErrorMesages}
                        <button id="create-button" className='btn btn-success btn-outline col-xs-5'>
                          {this.props.action}
                        </button>
                      <a id="cancel-button" className="btn btn-danger btn-outline col-xs-5 col-xs-offset-2" href="#/admin/party" role="button">Atšaukti</a>
                  </form>
                </div>
              </div>
            </div>
          </div>
    );
  }
});

window.PartyCreateEditFormComponent = PartyCreateEditFormComponent;
