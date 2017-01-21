var DistrictRepresentativeCreateFormComponent = React.createClass({
	  render: function() {
		    var districts = [];
		    this.props.districtList.map(function(district,index) {
		    	districts.push(
		        <option key={index} value={district.id}>{district.name}</option>
		      );
		    });

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
				              	<div className="form-heading">
				              		<h4> Registruoti apylinkės atstovą </h4>
				              	</div>
				              	<form onSubmit={this.props.onHandleSubmit} role="form">
				                <div className="input-group">
				                <label>Atstovo vardas</label>
				                  <input
				                    type="text"
				                    onChange={this.props.onHandleNameChange}
				                    value={this.props.name}
				                    className="form-control"
				                    placeholder="Vardas"
				                    required
				                  />
				                </div><br/>
				                <div className="input-group">
				                <label>Atstovo pavardė</label>
				                  <input
				                    type="text"
				                    onChange={this.props.onHandleSurnameChange}
				                    value={this.props.surname}
				                    className="form-control"
				                    placeholder="Pavardė"
				                    required
				                  />
				                </div><br/>
				                <div className="form-group">
				                  <label>Atstovaujama apylinkė:</label>
				                  <select value={this.props.district} onChange={this.props.onHandleDistrictChange} className="form-control">
				                    {districts}
				                  </select>
				                </div><br/>
				                <button className='btn btn-success btn-block'>
				                  {this.props.action}
				                </button>
				                </form>
				                <div>
				                  <a className="btn btn-danger btn-block" href="#/representative" role="button">Atšaukti</a>
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

window.DistrictRepresentativeCreateFormComponent = DistrictRepresentativeCreateFormComponent;
