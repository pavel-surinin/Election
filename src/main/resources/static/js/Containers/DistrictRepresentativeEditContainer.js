  var DistrictRepresentativeEditContainer = React.createClass({
	  getInitialState: function() {
		    return {
		      representativeDetails : [],
		      name : '',
		    };
		  },

		  componentWillMount: function() {
		    var self = this;
		    axios
		    .get('/representative/' + this.props.params.id)
		    .then(function(response){
		      self.setState({
		        id : response.data.id,
		        name: response.data.name,
		      });
		    })
		    .catch(function(err){
		      console.error('DistrictRepresentativeEditContainer.componentWillMount.axios.get/admin/representative/:id', err);
		    });
		  },

		  onHandleChangeName : function(event){
		    this.setState({name: event.target.value});
		  },

		  onHandleUpdate: function(event) {
		    var self = this;
		    axios
		    .put('/admin/representative/' + self.state.id,
		      {
		        id : self.state.id,
		        name: self.state.name,})
		    .then(function(response){
		      console.log(response.data);
		      self.context.router.push('/admin/representative');
		    })
		    .catch(function(err){
		      console.error('Update failed at DistrictRepresentativeEditContainer - ', err);
		    });
		    event.preventDefault();
		  },
		  onHandleCancel:function(){
		    this.context.router.push('/admin/representative');
		  },

		  render: function() {
		    return (
		      <DistrictRepresentativeEditFormComponent
		      submitButtonName='Išsaugoti'
		      
		      onHandleChangeName={this.onHandleChangeName}

		      onHandleUpdate={this.onHandleUpdate}
		      onHandleCancel={this.onHandleCancel}

		      name={this.state.name}
		      />

		    );
		  }
		});

    DistrictRepresentativeEditContainer.contextTypes = {
	  router: React.PropTypes.object.isRequired,
	};
	
	window.DistrictRepresentativeEditContainer = DistrictRepresentativeEditContainer ;
