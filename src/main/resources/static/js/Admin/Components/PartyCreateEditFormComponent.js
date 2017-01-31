var PartyCreateEditFormComponent = React.createClass({
  onFileChange : function(){
    this.props.onHandleFileChange(this.refs.file.files[0]);
  },

  render: function() {
    var nameErrorMesages = validation.showMsg(this.props.nameErrorMesages);
    var numberErrorMesages = validation.showMsg(this.props.numberErrorMesages);
    var fileErrorMesages = validation.showMsg(this.props.fileErrorMesages);

    return (
		        <div className="row">
		          <div className="col-md-6 col-md-offset-3 col-xs-12">
		            <div className="panel panel-primary">
                  <div className="panel-heading text-center">
                  <h4><b>Registruoti partiją</b></h4>
                  </div>
		              <div className="panel-body">
		                <form onSubmit={this.props.onHandleSubmit} role="form">
		                <div className="input-group col-xs-12 text-primary">
		                  <label>Partijos Pavadinimas</label>
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
		                    <div className="form-group text-primary">
		                      <label>Pasirinkite failą</label>


                            <label className="btn-bs-file btn btn-lg btn-primary btn-outline">
                                <input onChange={this.onFileChange} ref="file" type="file" name="file" id="file-select"/>
                            </label>

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
