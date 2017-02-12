var CountyCreateEditFormComponent = React.createClass({
  render: function() {
    var nameErrorMesages = validation.showMsg(this.props.nameErrorMesages);
    var message = '';
    if (this.props.nameClone) {
      message = (
        <div className="alert alert-danger">Apygarda su tokiu pavadinimu jau užregistruota.</div>
      );
    } else {
      message = '';
    }
    return (
        <div className="row">
          <div className="col-md-6 col-md-offset-3 col-xs-12">
            <div className="panel panel-primary">
            <div className="panel-heading text-center">
            <h4><b>Registruoti apygardą</b></h4>
            </div>
              <div className="panel-body">
                <div className="form-heading">
                </div>
                <form onSubmit={this.props.onHandleSubmit} role="form">
                <div className="input-group col-xs-12 text-primary">
                <label>Apygardos pavadinimas</label>
                  <input
                    id="name-input"
                    type="text"
                    onChange={this.props.onHandleNameChange}
                    value={this.props.name}
                    className="form-control"
                    placeholder="Apygardos pavadinimas"
                    required
                  />
                </div>
                <br/>
                {nameErrorMesages}
                {message}
                <br/>
                <div className='text-center'>
                <button id="create-button" className='btn btn-success btn-outline col-xs-5'>
                  {this.props.action}
                </button>
                <a id="delete-button" className="btn btn-danger btn-outline col-xs-5 col-xs-offset-2" href="#/admin/county" role="button">Atšaukti</a>
                </div>
                </form>
              </div>
            </div>
          </div>
        </div>
    );
  }

});

window.CountyCreateEditFormComponent = CountyCreateEditFormComponent;
