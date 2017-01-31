var DistrictRepresentativeEditFormComponent = React.createClass({
	  render: function() {
		    return (

		      <div className="container vertical-center">
		        <div className="row">
		          <div className="col-md-6 col-md-offset-1 col-xs-6">
		            <div className="panel panel-default">
		              <div className="panel-body">
		                <form onSubmit={this.props.onHandleUpdate} role="form">
		                  <fieldset>
		                  	<div className="input-group">
		                  		<label>Naujas apylinkės atstovo vardas</label>
					                <input
				                      type="text"
				                      onChange={this.props.onHandleChangeName}
				                      value={this.props.name}
				                      className="form-control"
				                      placeholder="Naujas apulinkės atstovo vardas"
				                      name="name"
				                      required
				                    />
					              </div><br/>
	                            <button className="btn btn-lg btn-success btn-block">
	                              {this.props.submitButtonName}
	                            </button>
	                          </fieldset>
	                        </form>
	                      <button onClick={this.props.onHandleCancel} className="btn btn-lg btn-danger btn-block">
	                        Atšaukti
	                      </button>
		                </div>
		              </div>
		            </div>
		          </div>
		        </div>
		    );
		  }

		});

		window.DistrictRepresentativeEditFormComponent = DistrictRepresentativeEditFormComponent;
