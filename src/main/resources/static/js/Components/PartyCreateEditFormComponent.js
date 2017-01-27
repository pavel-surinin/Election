var PartyCreateEditFormComponent = React.createClass({
  onFileChange : function(){
    this.props.onHandleFileChange(this.refs.file.files[0]);
  },

  render: function() {
    var nameErrorMesages = validation.showMsg(this.props.nameErrorMesages);
    var message = '';
    if (this.props.nameClone) {
      message = (
          <div className="alert alert-danger" role="alert">
          <strong>Dėmesio!</strong> Tokia partija jau užregistruota.
          </div>
        );
    } else {
      message = '';
    }
    return (
		        <div className="row">
		          <div className="col-md-6 col-md-offset-1">
		            <div className="panel panel-default">
		              <div className="panel-body">
		                <form onSubmit={this.props.onHandleSubmit} role="form">
		                <div className="input-group col-xs-12">
		                  <label>Partijos Pavadinimas</label>
		                    <input
		                      type="text"
		                      onChange={this.props.onHandleNameChange}
		                      value={this.props.name}
		                      className="form-control"
		                      placeholder="Pavadinimas"
		                      required
		                    />
		                </div>
                    <div className="input-group col-xs-12">
		                  <label>Partijos numeris</label>
		                    <input
		                      type="text"
		                      onChange={this.props.onHandleNumberChange}
		                      value={this.props.number}
		                      className="form-control"
		                      placeholder="Partijos Sąrašo Numeris"
		                    />
		                </div><br/>
                      {nameErrorMesages}
		                    <div className="form-group">
		                      <label>Pasirinkite failą</label>

		                      <input onChange={this.onFileChange} ref="file" type="file" name="file" className="form-control-file"/>

		                      <p className="help-block">Pasirinkite ir įkelkite partijos sąrašo failą CSV formatu </p>
		                  </div><br/>
		                  <button className='btn btn-success btn-block'>
		                    {this.props.action}
		                  </button>
		                </form>
                    {message}
		                  <div>
		                    <a className="btn btn-danger btn-block" href="#/admin/party" role="button">Atšaukti</a>
		                  </div>
		              </div>
		            </div>
		          </div>
		        </div>
    );
  }
});

window.PartyCreateEditFormComponent = PartyCreateEditFormComponent;
