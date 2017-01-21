var CountyCreateEditFormComponent = React.createClass({
  render: function() {
    return (
        	<div className="container-fluid">
    		  <div className="row">
    			<div className="col-xs-12 col-sm-3 col-md-3 col-lg-3 sidenav">
    			  <SideNavBarComponent />
    			</div>
    			<div className="col-xs-12 col-sm-9 col-md-9 col-lg-9 text-left">
  		      <div className="container vertical-center">
  		        <div className="row">
  		          <div className="col-md-4 col-md-offset-1">
		            <div className="login-panel panel panel-default">
		              <div className="panel-body">
		                <form onSubmit={this.props.onHandleSubmit} role="form">
		                <div className="input-group">
		                <label>Apygardos Pavadinimas</label>
		                  <input
		                    type="text"
		                    onChange={this.props.onHandleNameChange}
		                    value={this.props.name}
		                    className="form-control"
		                    placeholder="Pavadinimas"
		                    required
		                  />
		                </div><br/>
		                <button className='btn btn-success btn-block'>
		                  {this.props.action}
		                </button>
		                </form>
		                <div>
		                  <a className="btn btn-danger btn-block" href="#/county" role="button">Atšaukti</a>
		                </div>
		              </div>
		            </div>
		          </div>
		        </div>
		      </div>
			</div>
		  </div>
		</div>
    );
  }

});

window.CountyCreateEditFormComponent = CountyCreateEditFormComponent;
