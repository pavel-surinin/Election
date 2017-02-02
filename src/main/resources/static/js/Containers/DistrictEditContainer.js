  var DistrictEditContainer = React.createClass({
	  getInitialState: function() {
		    return {
		      details : [],
		      name : '',
		      adress : '',
		      representativeId : '',
		      representativeName : '',
		      countyId : '',
		      countyName : '',
		    };
		  },

		  componentWillMount: function() {
		    var self = this;
		    axios
		    .get('/district/' + this.props.params.id)
		    .then(function(response){
		      self.setState({
		        id : response.data.id,
		        name: response.data.name,
		        adress: response.data.adress,
		        representativeId: response.data.representativeId,
			    representativeName: response.data.representativeName,
			    countyId: response.data.countyId,
			    countyName: response.data.countyName,
		      });
		    })
		    .catch(function(err){
		      console.error('DistrictEditContainer.componentWillMount.axios.get/admin/district/:id', err);
		    });
		  },

		  onHandleChangeName : function(event){
		    this.setState({name: event.target.value});
		  },

		  onHandleUpdate: function(event) {
		    var self = this;
		    axios
		    .put('/admin/district/' + self.state.id,
		      {
		        id : self.state.id,
		        name: self.state.name,})
		    .then(function(response){
		      console.log(response.data);
		      self.context.router.push('/admin/district');
		    })
		    .catch(function(err){
		      console.error('Update failed at DistrictEditContainer - ', err);
		    });
		    event.preventDefault();
		  },
		  onHandleCancel:function(){
		    this.context.router.push('/admin/district');
		  },

		  render: function() {
		    return (
		      <DistrictEditFormComponent
		      submitButtonName='Išsaugoti'
		      
		      onHandleChangeName={this.onHandleChangeName}

		      onHandleUpdate={this.onHandleUpdate}
		      onHandleCancel={this.onHandleCancel}

		      name={this.state.name}
		      />

		    );
		  }
		});

  DistrictEditContainer.contextTypes = {
	  router: React.PropTypes.object.isRequired,
	};
	
	window.DistrictEditContainer = DistrictEditContainer ;
